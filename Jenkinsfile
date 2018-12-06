pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '''cd Backend
mvn package'''
      }
    }
    stage('Test') {
      steps {
        sh '''cd Backend
mvn test'''
      }
    }
  }
}