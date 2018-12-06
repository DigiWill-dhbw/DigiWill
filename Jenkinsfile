pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cd Backend
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