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
        sh '''mvn test -DenvTarget=test jacoco:report'''
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
  stage('Test Report') {
    when {
      branch 'master'
    }
    steps {
      sh '''java -jar codacy-coverage-reporter.jar report -l Java -r ./target/site/jacoco/jacoco.xml'''
    }
  }
  post {
    always {
      junit '**/target/surefire-reports/TEST-*.xml'
      step( [ $class: 'JacocoPublisher' ] )
    }
  }
}