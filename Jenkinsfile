pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
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
