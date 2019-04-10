node {
    stage('Clone Code') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://gitlab.com/calebespinoza/hello-world-maven.git']]])
    }

    stage('Build') {
        sh 'ls'
        sh 'chmod +x mvnw'
        sh './mvnw clean compile'
        sh './mvnw package'
    }
    
    /*stage('Copy Archive') {
         steps {
             script {
                 step ([$class: 'CopyArtifact',
                 projectName: 'Create_archive',
                 filter: "packages/infra*.zip",
                 target: 'Infra']);
             }
         }
     }*/
}