apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: gp3
provisioner: ebs.csi.aws.com
volumeBindingMode: WaitForFirstConsumer
parameters:
  type: gp3
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: postgres
  namespace: ecommerce
spec:
  serviceName: postgres
  replicas: 1
  selector:
    matchLabels: { app: postgres }
  template:
    metadata:
      labels: { app: postgres }
    spec:
      containers:
        - name: postgres
          image: postgres:16
          ports: [{ containerPort: 5432 }]
          env:
            - { name: POSTGRES_DB, value: ecommerce }
            - { name: POSTGRES_USER, value: ecommerce_user }
            - name: POSTGRES_PASSWORD
              valueFrom: { secretKeyRef: { name: db-secret, key: password } }
            - { name: PGDATA, value: /var/lib/postgresql/data/pgdata }
          volumeMounts:
            - { name: data, mountPath: /var/lib/postgresql/data }
          resources:
            requests: { cpu: 100m, memory: 256Mi }
  volumeClaimTemplates:
    - metadata: { name: data }
      spec:
        accessModes: ["ReadWriteOnce"]
        storageClassName: gp3
        resources: { requests: { storage: 5Gi } }
---
apiVersion: v1
kind: Service
metadata: { name: postgres, namespace: ecommerce }
spec:
  selector: { app: postgres }
  ports: [{ port: 5432 }]
---
apiVersion: apps/v1
kind: Deployment
metadata: { name: backend, namespace: ecommerce }
spec:
  replicas: 1
  selector:
    matchLabels: { app: backend }
  template:
    metadata:
      labels: { app: backend }
    spec:
      containers:
        - name: backend
          image: aycha123/ecommerce-backend:${TAG}
          ports: [{ containerPort: 8080 }]
          env:
            - { name: SPRING_DATASOURCE_URL, value: "jdbc:postgresql://postgres:5432/ecommerce" }
            - { name: SPRING_DATASOURCE_USERNAME, value: ecommerce_user }
            - { name: SERVER_PORT, value: "8080" }
            - name: SPRING_DATASOURCE_PASSWORD
              valueFrom: { secretKeyRef: { name: db-secret, key: password } }
          resources:
            requests: { cpu: 200m, memory: 512Mi }
---
apiVersion: v1
kind: Service
metadata: { name: backend, namespace: ecommerce }
spec:
  selector: { app: backend }
  ports: [{ port: 8080 }]
---
apiVersion: apps/v1
kind: Deployment
metadata: { name: frontend, namespace: ecommerce }
spec:
  replicas: 1
  selector:
    matchLabels: { app: frontend }
  template:
    metadata:
      labels: { app: frontend }
    spec:
      containers:
        - name: frontend
          image: aycha123/ecommerce-frontend:${TAG}
          ports: [{ containerPort: 8080 }]
          resources:
            requests: { cpu: 50m, memory: 64Mi }
---
apiVersion: v1
kind: Service
metadata: { name: frontend, namespace: ecommerce }
spec:
  type: LoadBalancer
  selector: { app: frontend }
  ports: [{ port: 80, targetPort: 8080 }]