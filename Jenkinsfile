def serverBaseName = 'localhost'
def clusterName = 'SEC3ServerCluster'
def appName = 'SbeBackEndEAR2'
def virtualHost = 'ebac_host'
def appDestination = 'CR'
def serverQuantity = 2
def dirJar = '/SbeBackEndEAR/target/SbeBackEndEAR Install Files.ear'

node {
    def server = Artifactory.newServer url: "${env.SERVER_URL}", credentialsId: "${env.CREDENTIALS}"
    def rtMaven = Artifactory.newMavenBuild()

    stage('Clone Code') {
        checkout([$class: 'GitSCM', 
        branches: [[name: '*/master']], 
        doGenerateSubmoduleConfigurations: false, 
        extensions: [], 
        submoduleCfg: [], 
        userRemoteConfigs: [[url: 'https://gitlab.com/calebespinoza/hello-world-maven.git']]])
    }

    stage('Build') {
        sh 'ls'
        sh 'chmod +x mvnw'
        sh './mvnw clean compile'
        sh './mvnw package'
    }

    stage('Archive Artifact') {
        archiveArtifacts artifacts: '**/target/*.jar', 
        fingerprint: true, 
        onlyIfSuccessful: true
    }

    stage('Artifactory Configuration') {
        rtMaven.tool = "${env.MAVEN_TOOL}"
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        def buildInfo = Artifactory.newBuildInfo()
    }

    stage('Exec Maven') {
        rtMaven.run pom: 'helloworld/pom.xml', goals: 'clean install', buildInfo: buildInfo
    }

    stage('Publish build info'){
        server.publishBuildInfo buildInfo
    }

    /*stage('Trigger Promotion') {
        build job: 'Promotion1', 
        parameters: [string(name: 'serverBaseName1', value: "${serverBaseName}"),
        string(name: 'JobName', value: "${env.JOB_NAME}")]
        //echo "${env.JOB_NAME}"
    }*/
}