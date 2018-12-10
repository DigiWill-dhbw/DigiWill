pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cp /var/lib/jenkins/workspace/secrets-test.properties ./Backend/src/test/resources/secrets-test.properties
cd Backend
mvn clean install -DskipTests'''
      }
    }
    stage('Test') {
      steps {
        sh '''cd Backend
mvn test -DenvTarget=test'''
      }
    }
  }
}