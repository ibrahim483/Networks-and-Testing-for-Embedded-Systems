// Jenkins Declarative Pipeline för Autopark (Maven + JUnit 5)
// Kör: Checkout -> Build -> Test -> Coverage -> Package -> Arkivering

pipeline {
    agent any

    options {
        timestamps()                                   // tidsstämplar i loggen
        buildDiscarder(logRotator(numToKeepStr: '20')) // spara senaste 20 byggen
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    triggers {
        // Jenkins kollar GitHub var 5:e minut efter nya commits.
        // (Webhook är snabbare men kräver att Jenkins nås utifrån - se JENKINS-SETUP.md)
        pollSCM('H/5 * * * *')
    }

    environment {
        PROJECT_DIR = 'Autopark'
        MAVEN_OPTS  = '-Dmaven.repo.local=.m2repo'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                sh 'git log -1 --pretty=format:"Bygger commit: %h %s (%an)"'
            }
        }

        stage('Build') {
            steps {
                dir(env.PROJECT_DIR) {
                    sh 'mvn -B -ntp clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir(env.PROJECT_DIR) {
                    sh 'mvn -B -ntp test'
                }
            }
            post {
                always {
                    // Publicerar JUnit-resultaten så de syns som graf/trend i Jenkins
                    junit allowEmptyResults: true,
                          testResults: "${env.PROJECT_DIR}/target/surefire-reports/*.xml"
                }
            }
        }

        stage('Coverage') {
            steps {
                dir(env.PROJECT_DIR) {
                    sh 'mvn -B -ntp jacoco:report'
                }
                recordCoverage(
                    tools: [[parser: 'JACOCO',
                             pattern: "${env.PROJECT_DIR}/target/site/jacoco/jacoco.xml"]],
                    sourceCodeRetention: 'EVERY_BUILD'
                )
            }
        }

        stage('Package') {
            steps {
                dir(env.PROJECT_DIR) {
                    sh 'mvn -B -ntp package -DskipTests'
                }
                archiveArtifacts artifacts: "${env.PROJECT_DIR}/target/*.jar",
                                 fingerprint: true,
                                 allowEmptyArchive: true
            }
        }
    }

    post {
        success  { echo "✅ Bygget lyckades - #${env.BUILD_NUMBER}" }
        unstable { echo "⚠️  Bygget gick igenom men tester failade - #${env.BUILD_NUMBER}" }
        failure  { echo "❌ Bygget misslyckades - #${env.BUILD_NUMBER}" }
        always   { cleanWs(notFailBuild: true) }
    }
}
