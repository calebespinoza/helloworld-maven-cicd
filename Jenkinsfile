node() {
    try {
        stage('Clone Code') {
            git 'https://gitlab.com/calebespinoza/hello-world-maven.git'
        }

        stage('Compile') {
            if ( isUnix() ) {
                sh 'ls'
                sh 'chmod +x mvnw'
                sh './mvnw clean compile'
            } else {
                bat 'ls'
                bat 'mvnw clean compile'
            }
        }

        stage('Build') {
            if( isUnix() ) {
                sh './mvnw package'
                sh 'pwd'
            } else {
                bat 'mvnw package'
                bat 'pwd'
            }
        }

        stage('Archive Artifact') {
            archiveArtifacts artifacts: '**/target/*.jar', 
            fingerprint: true, 
            onlyIfSuccessful: true
        }

        stage ('Promote Stage') {
			echo 'Calling promoting build'
			build job: 'Promotion1', 
            parameters: [
                string(name: 'JobName', value: "${env.JOB_NAME}"),
                string(name: 'BuildNumber', value: "${env.BUILD_NUMBER}"),
                string(name: 'Author', value: "${env.GIT_COMMITTER_NAME}"),
                string(name: 'Commit', value: "${env.GIT_COMMIT}"),
                string(name: 'Branch', value: "${env.GIT_BRANCH}")
            ]
		}
    } catch (Exception e) {
        currentBuild.result = "FAILED"
    }
}

