pipeline {
    agent any
    tools {
        maven 'Maven' //引号里的Maven不要改
    }
    stages {
        stage('Test') {
            steps {
                withSonarQubeEnv('SonarQube') { //引号里的SonarQube不要改
                    sh 'mvn clean sonar:sonar'
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package'
                archive 'target/TestProgram-1.0-jar-with-dependencies.jar' //制品路径需要修改
            }
        }
        stage('Deploy') {
            steps { //所有的group1_demo需要修改
                sh 'docker stop FreeCoding-R_TestProgram || true'
                sh 'docker rm FreeCoding-R_TestProgram || true'
                sh 'docker run --name FreeCoding-R_TestProgram -p 13111:8080 -d dordoka/tomcat' //端口11111需要修改
                sh 'docker cp target/TestProgram-1.0-jar-with-dependencies.jar FreeCoding-R_TestProgram:/opt/tomcat/webapps/' //制品路径需要修改
            }
        }
    }
}
