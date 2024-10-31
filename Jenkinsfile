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
                    chmod +x gradlew  # gradlew 파일에 실행 권한 부여
                    ./gradlew clean bootJar --no-build-cache -x test
                '''
            }
        }

        stage('Prepare Environment') {
            steps {
                echo 'Ensuring /app directory exists and copying .env file...'
                sh '''
                    mkdir -p /app  # 이미 있으면 무시하고, 없으면 생성
                    cp /home/ubuntu/config/.env /app/.env || true  # /app/.env 경로로 복사
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
