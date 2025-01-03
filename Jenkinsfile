pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pull the latest code from the repository
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Build the project
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                // Run the tests
                sh './gradlew test'
            }
        }
    }

    post {
        success {
            echo 'Build and tests completed successfully.'
        }
        failure {
            echo 'Build or tests failed. Please check the logs for more details.'
        }
    }
}
