pipeline {
    agent any

    tools {
        maven 'Maven 3.8.4'
        jdk 'jdk17'
    }

    environment {
        REPORT_DIR = "test-output"
        JIRA_SITE = 'jira-auth' // 🔁 Replace 'jira' with your Jira site name configured in Jenkins
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

        stage('Update Jira') {
            steps {
                script {
                    // 🔍 Extract Jira issue keys like SDET-123 from Git commits
                    def issueKeys = currentBuild.changeSets.collectMany { changeSet ->
                        changeSet.items.collectMany { item ->
                            def matcher = (item.msg =~ /([A-Z]+-\d+)/)
                            matcher.collect { it[0] }
                        }
                    }.unique()

                    if (issueKeys) {
                        echo "Found Jira Issues: ${issueKeys}"
                        issueKeys.each { key ->
                            // 📝 Add comment to Jira issue
                            jiraAddComment idOrKey: key, comment: "✅ Jenkins Build #${env.BUILD_NUMBER} passed for job *${env.JOB_NAME}*."
                            // 🔁 Optionally, transition the issue to 'Done'
                            jiraIssueTransition idOrKey: key, transition: 'DONE'
                        }
                    } else {
                        echo "No Jira issue keys found in commit messages."
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Running always block in post"
            script {
                if (fileExists("${env.REPORT_DIR}/extent-report.html")) {
                    archiveArtifacts artifacts: 'test-output/*.html', allowEmptyArchive: true
                } else {
                    echo "No report found to archive."
                }
            }
        }

        success {
            echo "Running success block in post"
            emailext(
                subject: "✅ Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Good news! The Jenkins build succeeded.</p>
                         <p><b>Job:</b> ${env.JOB_NAME}<br>
                         <b>Build #:</b> ${env.BUILD_NUMBER}<br>
                         <b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                mimeType: 'text/html',
                to: 'hichamberr480@gmail.com',
                attachmentsPattern: "${env.REPORT_DIR}/extent-report.html",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
            )
        }

        failure {
            echo "Running failure block in post"
            emailext(
                subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Unfortunately, the Jenkins build failed.</p>
                         <p><b>Job:</b> ${env.JOB_NAME}<br>
                         <b>Build #:</b> ${env.BUILD_NUMBER}<br>
                         <b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                mimeType: 'text/html',
                to: 'hichamberr480@gmail.com',
                attachmentsPattern: "${env.REPORT_DIR}/extent-report.html",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']]
            )
        }
    }
}
