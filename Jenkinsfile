node() {
    try {
        stage('Clone Code') {
            git 'https://github.com/calebespinoza/helloworld-maven-cicd.git'
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
            if( isUnix() ) {
                sh "mv target/helloworld-artifact-1.0-SNAPSHOT.jar	target/helloworld-artifact-${BUILD_NUMBER}.jar"
            } else {
                bat 'mv target/helloworld-artifact-1.0-SNAPSHOT.jar	target/helloworld-artifact-${BUILD_NUMBER}.jar'
            }
            archiveArtifacts artifacts: '**/target/*.jar', 
            fingerprint: true, 
            onlyIfSuccessful: true
        }

        stage('Deploy on Development'){
            if( isUnix() ) {
                sh "java -jar target/helloworld-artifact-${BUILD_NUMBER}.jar"
            } else {
                bat 'java -jar target/helloworld-artifact-${BUILD_NUMBER}.jar'
            }
        }

        stage ('Promotion to QA') {
			echo 'Calling promoting build'
			build job: 'PromotionToQA',
            parameters: [
                string(name: 'JobName', value: "${env.JOB_NAME}"),
                string(name: 'BuildNumber', value: "${env.BUILD_NUMBER}"),
                string(name: 'Author', value: "${env.GIT_COMMITTER_NAME}"),
                string(name: 'Commit', value: "${env.GIT_COMMIT}"),
                string(name: 'Branch', value: "${env.GIT_BRANCH}"),
                string(name: 'Artifact', value: "helloworld-artifact-${env.BUILD_NUMBER}.jar")
            ]
		}
    } catch (Exception e) {
        currentBuild.result = "FAILED"
    }
}

