pipeline {
    agent any

    environment {
        ENV_FILE_SOURCE = '/home/ubuntu/.env'          // 원본 .env 파일 경로
        ENV_FILE_TARGET = '/var/jenkins_home/workspace/SpringServer/.env'  // Jenkins workspace 내 복사될 경로
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo 'Checking out SCM...'
                checkout scm
            }
        }

        stage('Copy .env File') {
            steps {
                echo 'Copying .env file to workspace...'
                sh "cp ${ENV_FILE_SOURCE} ${ENV_FILE_TARGET}"
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
                    docker-compose --env-file ${ENV_FILE_TARGET} up -d --build
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
