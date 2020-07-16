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
        
        //System Provided
        deploymentRepoName = 'springboot-web-app'
        deploymentRepoAddress = 'springboot-web-app'
        projectName = "springboot-web-app"
        branchName = BRANCH_NAME.toLowerCase()
        quitPipeline = false

      }
    
    stages {
        stage('Preparation') {
            steps {
                script {
                    
                        tagName = "planned-qmg-${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
                    
                    echo "branchName:${branchName}, tagName:${tagName}"

                    abortPreviousRunningBuilds()
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
