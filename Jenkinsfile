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
                git url: 'https://github.com/hichamberr/SDETPortfolioProject.git', branch: 'master'
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

        success {
            emailext(
                subject: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Good news! The Jenkins build succeeded.</p>
                         <p><b>Job:</b> ${env.JOB_NAME}<br>
                         <b>Build #:</b> ${env.BUILD_NUMBER}<br>
                         <b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                mimeType: 'text/html',
                to: 'your_email@gmail.com'
            )
        }

        failure {
            emailext(
                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Unfortunately, the Jenkins build failed.</p>
                         <p><b>Job:</b> ${env.JOB_NAME}<br>
                         <b>Build #:</b> ${env.BUILD_NUMBER}<br>
                         <b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                mimeType: 'text/html',
                to: 'your_email@gmail.com'
            )
        }
    }
}
