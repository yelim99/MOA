pipeline {
    agent any

    environment {
        ENV_FILE_PATH = '/home/ubuntu/.env'  // .env 파일의 경로를 변수로 설정
    }

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

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Deploying with Docker Compose...'
                sh '''
                    # 새로운 이미지를 빌드하고 모든 컨테이너 실행
                    docker-compose --env-file ${ENV_FILE_PATH} up -d --build
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
