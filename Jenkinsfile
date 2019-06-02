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
      agent { label 'maven:3.6.1-jdk-11' }
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
    stage('Firefox') {
      when {
        not {
          branch 'release'
        }
      }
      steps {
        sh '''mvn test -Dtest=CucumberTest -DenvTarget=test -Dbrowser=firefox jacoco:report'''
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