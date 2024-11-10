pipeline {
    agent any

    stages {
        
        stage('Checkout SCM') {
            steps {
                echo 'Checking out SCM...'
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                echo 'Building JAR file...'
                sh '''
                    cd backend
                    chmod +x gradlew
                    ./gradlew clean bootJar --no-build-cache -x test
                '''
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Deploying with Docker Compose...'
                sh '''
                    docker-compose down
                    docker-compose up -d --build
                '''
            }
        }

        stage('Initialize MongoDB Replica Set') {
            steps {
                echo 'Initializing MongoDB Replica Set...'
                // Execute the replica set initiation script in one of the MongoDB containers
                sh '''
                    docker exec mongo1 bash -c "mongo < /docker-entrypoint-initdb.d/mongo-init-replica.sh"
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
