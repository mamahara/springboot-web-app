def releaseVersion, releaseTicket, branchToCheckout, uploadFlag

pipeline {
    agent any
    
    options {
        skipStagesAfterUnstable()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
  
    stages {
        
        stage('Build App') {
            
            steps {
                script {
                    
                        sh '''
                            mvn clean install
                        '''
                    
                }
            }
            
    }
    }	//end Stages
    post {
        
    }
}
