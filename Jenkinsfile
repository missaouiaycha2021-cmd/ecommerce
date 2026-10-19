pipeline {
    agent any

    tools {
        maven 'maven-3'     // Manage Jenkins > Tools : nom exact
        nodejs 'node-20'    // Manage Jenkins > Tools : nom exact
    }

    environment {
        DOCKERHUB_USER = 'aycha123'
        BACKEND_IMAGE  = 'aycha123/ecommerce-backend'
        FRONTEND_IMAGE = 'aycha123/ecommerce-frontend'
        BACKEND_DIR    = 'api'                   // Spring Boot (pom.xml)
        FRONTEND_DIR   = 'mon-projet-angular'   // Angular (package.json)
        SONAR_PROJECT  = 'mon-projet-api'
        SNYK_TOKEN     = credentials('snyk-token')

        // Valeurs de TEST pour la base PostgreSQL temporaire du scan DAST (pas des vrais secrets)
        DAST_DB        = 'ecommerce'
        DAST_DB_USER   = 'ci_user'
        DAST_DB_PASS   = 'ci_pass_test'

    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.IMAGE_TAG = env.GIT_COMMIT.take(7)
                    echo "Tag des images : ${env.IMAGE_TAG}"
                }
            }
        }

        stage('Gitleaks (secrets)') {
            steps {
                // Scan des fichiers du dépôt. Échoue (rouge) si un secret est trouvé.
                sh '''
                    docker run --rm --user $(id -u):$(id -g) \
                        -v "$WORKSPACE":/repo \
                        zricethezav/gitleaks:latest detect \
                        --source /repo --no-git --redact \
                        --report-format json --report-path /repo/gitleaks-report.json \
                        --exit-code 1
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: 'gitleaks-report.json', allowEmptyArchive: true
                }
            }
        }

        stage('Build') {
            parallel {
                stage('Backend (Maven)') {
                    steps {
                        dir("${BACKEND_DIR}") {
                            sh 'mvn -B clean package -DskipTests'
                        }
                    }
                }
                stage('Frontend (Angular)') {
                    steps {
                        dir("${FRONTEND_DIR}") {
                            sh 'npm ci'
                            sh 'npm run build -- --configuration production'
                        }
                    }
                }
            }
        }

        stage('Tests') {
            steps {
                dir("${BACKEND_DIR}") {
                    sh 'mvn -B test'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: "${BACKEND_DIR}/target/surefire-reports/*.xml"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir("${BACKEND_DIR}") {
                    withSonarQubeEnv('sonarqube') {
                        sh """
                            mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                              -Dsonar.projectKey=${SONAR_PROJECT} \
                              -Dsonar.projectName=${SONAR_PROJECT}
                        """
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Snyk Scan') {
            steps {
                // Un catchError par dossier : si le backend a des failles, le frontend est quand meme scanne
                catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
                    dir("${BACKEND_DIR}") {
                        sh 'snyk test --severity-threshold=high'
                    }
                }
                catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
                    dir("${FRONTEND_DIR}") {
                        sh 'snyk test --severity-threshold=high'
                    }
                }
            }
        }

        stage('Docker Build & Tag') {
            steps {
                sh """
                    docker build -t ${BACKEND_IMAGE}:${IMAGE_TAG} ${BACKEND_DIR}
                    docker tag   ${BACKEND_IMAGE}:${IMAGE_TAG} ${BACKEND_IMAGE}:latest

                    docker build -t ${FRONTEND_IMAGE}:${IMAGE_TAG} ${FRONTEND_DIR}
                    docker tag   ${FRONTEND_IMAGE}:${IMAGE_TAG} ${FRONTEND_IMAGE}:latest
                """
            }
        }

        stage('Trivy Scan') {
            steps {
                // Retirez --exit-code 1 pour une démonstration si une base d'image a une faille CRITICAL
                sh """
                    trivy image --severity CRITICAL --exit-code 1 --ignore-unfixed \
                        --format table -o trivy-backend.txt ${BACKEND_IMAGE}:${IMAGE_TAG}
                    trivy image --severity CRITICAL --exit-code 1 --ignore-unfixed \
                        --format table -o trivy-frontend.txt ${FRONTEND_IMAGE}:${IMAGE_TAG}
                """
            }
            post {
                always {
                    archiveArtifacts artifacts: 'trivy-*.txt', allowEmptyArchive: true
                }
            }
        }

        stage('DAST (OWASP ZAP)') {
            steps {
                // Démarre PostgreSQL + backend + frontend dans un réseau Docker temporaire,
                // lance ZAP baseline contre l'application, puis nettoie (voir post).
                // UNSTABLE (jaune) si l'application ne démarre pas ou si ZAP signale un problème.
                catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
                    sh '''
                        set -e
                        docker network create ci-net || true
                        docker rm -f postgres-ci backend-ci frontend-ci || true

                        docker run -d --name postgres-ci --network ci-net \
                            -e POSTGRES_DB=$DAST_DB -e POSTGRES_USER=$DAST_DB_USER \
                            -e POSTGRES_PASSWORD=$DAST_DB_PASS postgres:16

                        docker run -d --name backend-ci --network ci-net -e SERVER_PORT=8080 \
                            -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-ci:5432/$DAST_DB \
                            -e SPRING_DATASOURCE_USERNAME=$DAST_DB_USER \
                            -e SPRING_DATASOURCE_PASSWORD=$DAST_DB_PASS \
                            -e SERVER_PORT=8080 \
                            $BACKEND_IMAGE:$IMAGE_TAG

                        docker run -d --name frontend-ci --network ci-net $FRONTEND_IMAGE:$IMAGE_TAG

                        echo "Attente du démarrage (max 5 min)..."
                        for i in $(seq 1 60); do
                            if docker run --rm --network ci-net curlimages/curl -s -o /dev/null http://backend-ci:8080/ \
                               && docker run --rm --network ci-net curlimages/curl -s -o /dev/null http://frontend-ci:8080/; then
                                echo "Application prête."; break
                            fi
                            sleep 5
                        done

                        echo "---- Derniers logs du backend (pour le diagnostic) ----"
                        docker logs --tail 60 backend-ci || true

                        mkdir -p zap-reports && chmod 777 zap-reports

                        # -j : spider AJAX (utile pour Angular) ; -I : ne pas échouer sur simples avertissements
                        docker run --rm --network ci-net -v "$WORKSPACE/zap-reports":/zap/wrk:rw \
                            ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
                            -t http://frontend-ci:8080 -j -I -r zap-frontend.html

                        if docker run --rm --network ci-net curlimages/curl -s -o /dev/null http://backend-ci:8080/; then
                            docker run --rm --network ci-net -v "$WORKSPACE/zap-reports":/zap/wrk:rw \
                                ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
                                -t http://backend-ci:8080 -I -r zap-backend.html
                        else
                            echo "Backend injoignable : scan ZAP du backend ignore (voir les logs ci-dessus)"
                        fi
                    '''
                }
            }
            post {
                always {
                    sh 'docker rm -f postgres-ci backend-ci frontend-ci || true'
                    sh 'docker network rm ci-net || true'
                    archiveArtifacts artifacts: 'zap-reports/*.html', allowEmptyArchive: true
                }
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'registry-creds',
                                                  usernameVariable: 'REG_USER',
                                                  passwordVariable: 'REG_PASS')]) {
                    sh '''
                        echo "$REG_PASS" | docker login -u "$REG_USER" --password-stdin
                        docker push $BACKEND_IMAGE:$IMAGE_TAG
                        docker push $BACKEND_IMAGE:latest
                        docker push $FRONTEND_IMAGE:$IMAGE_TAG
                        docker push $FRONTEND_IMAGE:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
        success {
            echo "CI OK : images ${BACKEND_IMAGE}:${IMAGE_TAG} et ${FRONTEND_IMAGE}:${IMAGE_TAG} poussées."
        }
        failure {
            echo 'CI en échec : consultez le stage rouge dans la Stage View.'
        }
    }
}