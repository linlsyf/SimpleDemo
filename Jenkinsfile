pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M4"
    }

    stages {
        stage('下载编译') {
             steps {
             helloword()
             }
            steps {
                
                // Get some code from a GitHub repository
                git 'https://github.com/linlsyf/SimpleDemo.git'

                // Run Maven on a Unix agent.
                bat "mvn package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                   // junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

         stage('docker打包') {
               steps {

            bat " cd C:/Users/Administrator/.jenkins/workspace/pipline@2"
              bat "  docker build -t hello:hello C:/Users/Administrator/.jenkins/workspace/pipline@2"



              }


            }

         stage('运行') {
               steps {
            bat " cd C:/Users/Administrator/.jenkins/workspace/pipline@2"


                  bat " docker run -itd  -p 1021:1021 --name hello hello:hello"


              }


            }
    }
}
def  helloword(){
  println("hello word  1111")
}
