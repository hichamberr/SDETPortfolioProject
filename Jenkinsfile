pipeline {
    agent any

    tools {
        maven 'Maven 3.8.4'  // Make sure Jenkins has this tool name configured
        jdk 'jdk17'          // You can set this name in Jenkins Global Tool Config
    }

    environment {
        REPORT_DIR = "test-output"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/hichamberr/SDETPortfolioProject.git', branch: 'main'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${env.REPORT_DIR}",
                    reportFiles: 'extent-report.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

   post {
       always {
           script {
               if (fileExists("${env.REPORT_DIR}/extent-report.html")) {
                   archiveArtifacts artifacts: 'test-output/*.html', allowEmptyArchive: true
               } else {
                   echo "No report found to archive."
               }
           }
       }
   }
}