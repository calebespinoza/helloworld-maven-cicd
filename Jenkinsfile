def serverBaseName = 'localhost'
def clusterName = 'SEC3ServerCluster'
def appName = 'SbeBackEndEAR2'
def virtualHost = 'ebac_host'
def appDestination = 'CR'
def serverQuantity = 2
def dirJar = 'target/java-artifact-1.0-SNAPSHOT.jar'
def installName = "sec/backend/SbeBackEndJARInstallFiles_"+env.BUILD_TAG+".jar"

node {
    def server = Artifactory.server 'artifactory.server'
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo

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
        sh 'pwd'
    }

    stage('Archive Artifact') {
        archiveArtifacts artifacts: '**/target/*.jar', 
        fingerprint: true, 
        onlyIfSuccessful: true
    }

    stage ('Artifactory configuration') {
        rtMaven.tool = 'Maven_Local' // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        buildInfo = Artifactory.newBuildInfo()
    }

    stage ('Exec Maven') {
        rtMaven.run pom: '', goals: 'clean install', buildInfo: buildInfo
    }

    stage ('Publish build info') {
        server.publishBuildInfo buildInfo
    }

    stage ('Upload EARs to Artifactory by CURL'){
        def artUsr
    	def artPass
    	def JenkinPass
    	withCredentials([usernamePassword(credentialsId: 'jfrog.artifactory.server', passwordVariable: 'artPassword', usernameVariable: 'artUsername')]) {
    	    artUsr = env.artUsername
    		artPass = env.artPassword
    	}
    	addJarToArtifactory(artUsr, artPass, JenkinPass, "${dirJar}","${installName}")
    }

    /*stage('Trigger Promotion') {
        build job: 'Promotion1', 
        parameters: [string(name: 'serverBaseName1', value: "${serverBaseName}"),
        string(name: 'JobName', value: "${env.JOB_NAME}")]
        //echo "${env.JOB_NAME}"
    }*/
}

def addJarToArtifactory(artUsr, artPass, JenkinPass, dirJar, installName){
    sh "curl -u ${artUsr}:${artPass} -s  -X PUT --data-binary ${dirJar} http://10.211.55.4:8081/artifactory/BAC-Repositorio-Instalables/${installName}"
}