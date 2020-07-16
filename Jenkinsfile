pipeline {
    agent any
    
    environment {
        //User Provided
        //Build & Nexus Related
        nexusGroupFolder = 'com.learning.springboot-web-app'
        buildDirectoryAPI = './springboot-web-app/target/'
        buildFileAPI = 'springboot-web-app-1.0-SNAPSHOT.jar'
        nexusArtifactTypeAPI = 'jar'
        artifactApp = 'springboot-web-app'

      }
    
    stages {
        stage('Preparation') {
            steps {
                script {
                    
                        tagName = "planned-qmg-${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
                    
                    echo "branchName:${branchName}, tagName:${tagName}"

                    abortPreviousRunningBuilds()
                    (releaseTicket, releaseVersion) = common.getCommitMessageLinux()
                    }
            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
