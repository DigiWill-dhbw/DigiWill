pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cp /var/jenkins_home/workspace/secrets-test.properties ./src/test/resources/secrets-test.properties
mvn clean install -DskipTests'''
      }
    }
    stage('Test') {
      steps {
        sh '''mvn test -DenvTarget=test'''
      }
    }
    stage('Deploy') {
      steps {
        sh '''docker build --build-arg=target/*.jar -t digiwill .'''
        sh '''docker run -d --name digiwill -p 8085:8080 digiwill'''
       }
    }
  }
}