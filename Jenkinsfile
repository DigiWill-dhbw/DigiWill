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
    stage('Unit tests') {
      when {
        not {
          branch 'release'
         }
      }
      steps {
        sh '''mvn test -Dtest=!CucumberTest jacoco:report'''
      }
    }
    stage('Selenium tests') {
      when {
        not {
          branch 'release'
        }
      }
      parallel {
        stage('Chrome') {
          steps {
            sh '''mvn test -Dtest=CucumberTest -DenvTarget=test -Dbrowser=chrome jacoco:report'''
          }
        }
        stage('Firefox') {
          steps {
            sh '''mvn test -Dtest=CucumberTest -DenvTarget=test -Dbrowser=firefox jacoco:report'''
          }
        }
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
    stage('Test Report') {
      when {
        branch 'master'
      }
      steps {
        sh '''java -jar codacy-coverage-reporter.jar report -l Java -r ./target/site/jacoco/jacoco.xml'''
      }
    }
  }
  post {
    always {
      script {
        if (env.BRANCH_NAME != 'release') {
          junit '**/target/surefire-reports/TEST-*.xml'
          step( [ $class: 'JacocoPublisher' ] )
        }
      }
    }
    success {
      emailext (
                subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                  <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
    }
  }
}