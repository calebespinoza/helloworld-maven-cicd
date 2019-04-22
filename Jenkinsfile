def serverBaseName = 'localhost'
def clusterName = 'SEC3ServerCluster'
def appName = 'SbeBackEndEAR2'
def virtualHost = 'ebac_host'
def appDestination = 'CR'
def serverQuantity = 2
def dirEar = '/SbeBackEndEAR/target/SbeBackEndEAR Install Files.ear'

node {
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

    stage('Trigger Promotion') {
        build job: 'Promotion1', 
        parameters: [string(name: 'serverBaseName1', value: "${serverBaseName}"),
        string(name: 'JobName', value: "${env.JOB_NAME}")]
        /*parameters: [
			string(name: 'serverBaseName1', value: "${serverBaseName}"), 
			string(name: 'direccionEAR', value: "${dirEar}"), 
			string(name: 'virtualHost', value: "${virtualHost}"), 
			string(name: 'clusterName', value: "${clusterName}"), 
			string(name: 'appName', value: "${appName}")
        ]

        build 'Promotion1'*/
        echo "${env.JOB_NAME}"
    }
}