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
        sh '''docker stop digiwill_prod || true'''
        sh '''docker rm digiwill_prod || true'''
        sh '''docker rmi digiwill || true'''
        sh '''docker build --build-arg=target/*.jar -t digiwill .'''
        sh '''docker run -d --name digiwill_prod digiwill'''
      }
    }
  }
  post {
    always {
      junit '**/target/surefire-reports/TEST-*.xml'
      step( [ $class: 'JacocoPublisher' ] )
    }
  }
}