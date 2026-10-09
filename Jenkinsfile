pipeline {
    
  agent any      //Exécute le pipeline sur n’importe quelle machine Jenkins disponible.

  environment {
    REGISTRY     = 'docker.io/aycha123' //lien docker hub
    BACKEND_IMG  = "${REGISTRY}/backend" //lien image backend dans docker hub
    FRONTEND_IMG = "${REGISTRY}/frontend" //lien image frontend dans docker hub
    TAG          = "${env.GIT_COMMIT.take(7)}"//pour identifier chaque version de l’image
    CONFIG_REPO  = 'github.com/votre-user/mon-projet-config.git' //Dépôt GitHub qui contient le chart Helm
  }

  tools { //Jenkins utilise Maven et Node que vous avez configurés dans Manage Jenkins
    maven 'maven-3'
    nodejs 'node-20'
  }

  stages {

    stage('Checkout') { //Télécharge le code depuis GitHub
      steps { checkout scm }
    }

    stage('Build & Test Backend') { //compile Spring Boot et lance les tests, dans le dossier api
      steps {
        dir('api') { sh 'mvn clean verify' }
      }
    }

    stage('Build Frontend') { //npm ci installe les dépendances, npm run build compile Angular
      steps {
        dir('mon-projet-angular') {
          sh 'npm ci'
          sh 'npm run build'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('sonarqube') {
          dir('api') {
            sh 'mvn sonar:sonar -Dsonar.projectKey=mon-projet-api'
          }
        }
      }
    }

    stage('Quality Gate') { //	Attend le verdict de SonarQube (max 5 min). Si la qualité est insuffisante, le pipeline s’arrête
      steps {
        timeout(time: 5, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Snyk Scan') { //	Cherche les failles dans les dépendances (pom.xml, package.json).
      steps {
        withCredentials([string(credentialsId: 'snyk-token', variable: 'SNYK_TOKEN')]) {
          dir('api') { sh 'snyk test --severity-threshold=high || true' }
          dir('mon-projet-angular') { sh 'snyk test --severity-threshold=high || true' }
        }
      }
    }

    stage('Docker Build & Tag') {
      steps { //Construit les 2 images avec les Dockerfiles et leur donne le tag (ex. backend:a1b2c3d)
        sh """
          docker build -t ${BACKEND_IMG}:${TAG} ./api
          docker build -t ${FRONTEND_IMG}:${TAG} ./mon-projet-angular
        """
      }
    }

    stage('Trivy Image Scan') {
      steps { //Cherche les failles dans les images. --exit-code 1 fait échouer le pipeline si une faille CRITICAL est trouvée
        sh """
          trivy image --exit-code 1 --severity CRITICAL ${BACKEND_IMG}:${TAG}
          trivy image --exit-code 1 --severity CRITICAL ${FRONTEND_IMG}:${TAG}
        """
      }
    }

    stage('Docker Push') { //	Se connecte à Docker Hub et envoie les 2 images
      steps {
        withCredentials([usernamePassword(credentialsId: 'registry-creds',
            usernameVariable: 'REG_USER', passwordVariable: 'REG_PASS')]) {
          sh """
            echo \$REG_PASS | docker login -u \$REG_USER --password-stdin
            docker push ${BACKEND_IMG}:${TAG}
            docker push ${FRONTEND_IMG}:${TAG}
          """
        }
      }
    }

    stage('Update Helm Values') {//Modifie le tag dans le dépôt de config, ce qui déclenche ArgoCD
      steps {
        withCredentials([string(credentialsId: 'github-token', variable: 'GH_TOKEN')]) {
          sh """
            rm -rf config-repo
            git clone https://\$GH_TOKEN@${CONFIG_REPO} config-repo
            cd config-repo
            sed -i '/backend:/,/tag:/ s|tag: ".*"|tag: "${TAG}"|' mon-app/values.yaml
            sed -i '/frontend:/,/tag:/ s|tag: ".*"|tag: "${TAG}"|' mon-app/values.yaml
            git config user.email "jenkins@local"
            git config user.name "jenkins"
            git commit -am "Update image tag to ${TAG}"
            git push
          """
        }
      }
    }
    // ArgoCD détecte le commit dans le dépôt de config
    // et déploie le chart Helm sur Kubernetes automatiquement.
  }

  post {
    success { echo "Pipeline OK : image ${TAG} poussée, ArgoCD va déployer" }
    failure { echo "Pipeline en échec, vérifiez les logs" }
    always  { sh 'docker logout || true' }
  }
}