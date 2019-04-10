stages {
    stage('Clone Code') {
        git 'https://gitlab.com/calebespinoza/hello-world-maven.git'
    }

    stage('Build') {
        sh 'ls'
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