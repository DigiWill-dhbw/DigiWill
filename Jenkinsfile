pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cp /var/jenkins_home/workspace/secrets-test.properties ./src/test/resources/secrets-test.properties'''
        sh '''cp /var/jenkins_home/workspace/secrets-deploy.properties ./src/main/resources/secrets-deploy.properties'''
        sh '''mvn clean install -DskipTests'''
      }
    }
    stage('Test') {
      when {
        not {
          branch 'release'
        }
      }
      steps {
        sh '''mvn test -DenvTarget=test'''
      }
    }
    stage('Deploy') {
      when {
        branch 'release'
      }
      steps {
        try {
          sh '''docker stop digiwill_prod'''
          sh '''docker rm digiwill_prod'''
          sh '''docker rmi digiwill'''
        } catch(err) {
          echo err
        }
        sh '''docker build --build-arg=target/*.jar -t digiwill .'''
        sh '''docker run -d --name digiwill_prod digiwill'''
      }
    }
  }
}