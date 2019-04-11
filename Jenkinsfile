pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cp /var/jenkins_home/workspace/secrets-test.properties ./src/test/resources/secrets-test.properties'''
        sh '''mvn clean install -DskipTests'''
      }
    }
    stage('Test') {
      steps {
        sh '''mvn test -DenvTarget=test'''
      }
    }
    stage('Deploy') {
      steps {
        sh '''cp /var/jenkins_home/workspace/secrets-deploy.properties ./src/test/resources/secrets-deploy.properties'''
        sh '''docker build --build-arg=target/*.jar -t digiwill .'''
        sh '''docker run -d --name digiwill -p 8085:8080 digiwill'''
       }
    }
  }
}