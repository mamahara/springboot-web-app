pipeline {
    agent any
    
    tools { 
        maven 'maven' 
        
    }
    
    environment {
        //User Provided
        //Build & Nexus Related
        nexusGroupFolder = 'com.learning.springboot-web-app'
        buildDirectoryAPI = '/var/lib/jenkins/jobs/springboot-web-app/branches/master/workspace/target'
        buildFileAPI = 'springboot-web-app-0.0.1-SNAPSHOT.jar'
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
		    versionNo = 1.01
                    buildNo = 100
                    nexusArtifactId = "master" + '-' + versionNo + '-' + buildNo
                    nexusArtifactURL = "http://nexus3-my-openshift-learning.apps-crc.testing"
                    nexusLink = "http://nexus3-my-openshift-learning.apps-crc.testing/" + "maven-releases/com/learning/${nexusArtifactId}/springboot-web-app/${nexusArtifactId}-${artifactApp}.jar"

                    echo "nexusArtifactURL:${nexusArtifactURL}, tagName:${tagName}"
                    echo "nexusArtifactId:${nexusArtifactId}, nexusLink:${nexusLink}"
		  }
                    
            }
        }
        stage('Build') {
            steps {
                script {
                    configFileProvider([configFile('2a481c2c-399f-4234-83ff-30ff1e74e32a')]) {
                        sh '''
                            mvn clean install
                        '''
		    }
                }
            }
	    
        }
	stage('Publish Artifacts') {
            
            steps {
                script {
                    dir("${buildDirectoryAPI}") {
                        sh 'ls -lahr'
                        nexusUploader(nexusArtifactId, buildFileAPI, nexusArtifactTypeAPI, nexusGroupFolder, 'maven-releases', artifactApp)
                    }
                    
                }
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
