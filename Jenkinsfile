pipeline {
    agent {
        docker {
            image 'maven:3.6.3-openjdk-11'
            args '-u root'
        }
    }

    stages {
        stage('Building') {
            steps {
                echo 'Building...'
                sh 'mvn clean compile'
            }
            post {
                always {
                    sh 'mvn spring-boot:run'
                }
            }
        }
    }
}