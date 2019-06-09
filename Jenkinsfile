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
    /*
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
    */
    stage('Chrome') {
      when {
        not {
          branch 'release'
        }
      }
      steps {
        sh '''mvn test -Dtest=CucumberTest -DenvTarget=test -Dbrowser=chrome jacoco:report'''
      }
    }
    /*
    stage('Firefox') {
      when {
        not {
          branch 'release'
        }
      }
      steps {
        sh '''mvn test -Dtest=CucumberTest -DenvTarget=test -Dbrowser=firefox jacoco:report'''
      }
    }*/
    stage('Deploy') {
      when {
        branch 'release'
      }
      steps {
        sh '''docker stop digiwill_prod || true'''
        sh '''docker rm digiwill_prod || true'''
        sh '''docker rmi digiwill_deploy || true'''
        sh '''docker build --build-arg=target/*.jar -t digiwill_deploy -f deploy/Dockerfile .'''
        sh '''docker run -d --name digiwill_prod digiwill_deploy'''
      }
    }
    stage('Publish Image') {
      steps {
        sh '''rm ./src/test/resources/secrets-test.properties'''
        sh '''rm ./src/main/resources/secrets-deploy.properties'''
        sh '''mvn install -DskipTests'''
        sh '''docker rmi kucki/digiwill:latest || true'''
        sh '''docker build --build-arg=target/*.jar -t kucki/digiwill:latest .'''
        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
          sh '''docker push kucki/digiwill:latest'''
        }
      }
    }
    stage('SonarQube Report') {
      when {
        not {
          branch 'release'
        }
      }
      steps {
        sh "mvn sonar:sonar -Dsonar.projectKey=DigiWill-dhbw_DigiWill -Dsonar.organization=digiwill-dhbw -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${env.SONAR_LOGIN} -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml -Dsonar.branch.name=${env.BRANCH_NAME}"
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
    failure {
      emailext (
                subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                mimeType: 'text/html',
                replyTo: '$DEFAULT_REPLYTO',
                body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                  <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&</p>""",
                to: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                 [$class: 'RequesterRecipientProvider']])
        )
    }
    fixed {
      emailext (
                    subject: "FIXED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                    mimeType: 'text/html',
                    replyTo: '$DEFAULT_REPLYTO',
                    body: """<p>FIXED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                      <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&</p>""",
                    to: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                     [$class: 'RequesterRecipientProvider']])
        )
    }
  }
}