def releaseVersion, releaseTicket, branchToCheckout, uploadFlag

pipeline {
    agent any
    
    options {
        skipStagesAfterUnstable()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    environment {
        //User Provided
        //Build & Nexus Related
        nexusGroupFolder = 'com.emaratech.noqodi.ded-voucher'
        buildDirectoryAPI = './noqodi-ded-ui/target/'
        buildFileAPI = 'noqodi-ded-ui-1.0-SNAPSHOT.jar'
        nexusArtifactTypeAPI = 'jar'
        artifactApp = 'ded-voucher'

        //Sonar Related
        sonarProjectKey = "noqodi-ded-voucher"
        sonarSources = "./noqodi-ded-ui/src/main/"
        sonarTests = "./noqodi-ded-ui/src/test/"
        sonarBinaries = "*/target/classes/*"
        sonarLibraries = ""
        sonarExclusions = ""

        //Environment Related
        slackChannel = "noqodi"
        mailTo = "noqodi.team@emaratech.ae,devops.scm@emaratech.ae"
        PATH = "/opt/ibmjdk/java_1.8_64/bin:$PATH"

        //System Provided
        deploymentRepoName = 'ded-voucher-devops'
        deploymentRepoAddress = 'emt-devops-projects/ded-voucher-devops.git'
        projectName = "ded-voucher"
        branchName = BRANCH_NAME.toLowerCase()
        quitPipeline = false
    }
    stages {
        stage('Preparation') {
            steps {
                script {
                    tagName = branchName
                    if (branchName == 'develop' || branchName == 'develop-pipelinetest') {
                        tagName = "planned-qmg-${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
                    }

                    echo "branchName:${branchName}, tagName:${tagName}"

                    abortPreviousRunningBuilds()
                    (branchToCheckout, tagName, quitPipeline) = commonLib.getDeploymentEnv(branchName, tagName)
                    (releaseTicket, releaseVersion) = commonLib.getCommitMessageLinux()
                    uploadFlag = checkUploadArtifactCondition(releaseTicket)

                    def strArray = tagName.split('-')
                    versionNo = strArray[strArray.size() - 2]
                    buildNo = strArray[strArray.size() - 1]
                    nexusArtifactId = branchToCheckout + '-' + versionNo + '-' + buildNo
                    nexusArtifactURL = getInfo.getNexusUrl()
                    nexusLink = getNexusUrl('2') + "maven-releases/com/emaratech/noqodi/ded-voucher/${nexusArtifactId}/${artifactApp}/${nexusArtifactId}-${artifactApp}.jar"

                    echo "nexusArtifactURL:${nexusArtifactURL}, branchToCheckout:${branchToCheckout}, tagName:${tagName}, quitPipeline:${quitPipeline}"
                    echo "nexusArtifactId:${nexusArtifactId}, uploadFlag: ${uploadFlag}, nexusLink:${nexusLink}"
                }
            }
        }
        stage('Build App') {
            when {
                expression { quitPipeline == false }
            }
            steps {
                script {
                    configFileProvider([configFile('f17d8c3f-51e3-4027-877f-dc6030f92475')]) {
                        sh '''
                            mvn clean install
                        '''
                    }
                }
            }
            post {
                success {
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.ApplicationTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.TradingLicenseDownloadTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.VoucherAuthorizedTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.VoucherCapturedTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.VoucherInquiryTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.VoucherPaymentRetriedTest.xml", "noqodi")
                    PublishTestResultsToQMetry("noqodi-ded-ui/target/surefire-reports/TEST-com.noqodi.ded.VoucherPayTest.xml", "noqodi")
                }
            }
        }
        stage('Static Code Analysis') {
            when {
                expression { quitPipeline == false }
            }
            steps {
                script {
                    sonarScan.mvnProject(sonarProjectKey, releaseTicket)
                }
            }
        }
        stage('Publish Artifacts') {
            when {
                expression { quitPipeline == false }
            }
            steps {
                script {
                    dir("${buildDirectoryAPI}") {
                        sh 'ls -lahr'
                        nexusUploader(nexusArtifactId, buildFileAPI, nexusArtifactTypeAPI, nexusGroupFolder, 'maven-releases', artifactApp)
                    }
                    if (uploadFlag) {
                        setReleaseTicketFields(releaseTicket, 'Nexus', nexusLink)
                    }
                }
            }
        }
        stage('Trigger Deployment Pipeline') {
            steps {
                script {
                    gitCommitToAnotherRepoLinux('ci.server-pipeline', deploymentRepoName, deploymentRepoAddress, '', branchToCheckout, tagName, releaseVersion, releaseTicket, nexusLink)
                }
            }
        }
    } //end Stages
    post {
        failure {
            script {
                emailNotifier("FAILURE", releaseTicket, "${projectName} Build", tagName, mailTo)
            }
        }
        cleanup {
            workspaceCleanup(WORKSPACE)
        }
    }
}

