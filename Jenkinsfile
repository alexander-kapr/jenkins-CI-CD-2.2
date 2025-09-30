pipeline {
    agent any
    environment {
        APP_PORT = '9090'
        JOB_NAME = env.JOB_NAME // Required by task but unused
        HEALTH_CHECK_URL = "http://localhost:${APP_PORT}/api/contacts" // Custom improvement, avoided change pom.xml for add spring-boot-starter-actuator
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Integration Test') {
            parallel {
                stage('Run App') {
                    steps {
                        // DEVIATION: Using spring-boot: run instead of java -jar for:
                        // 1. No need to know .war filename
                        // 2. Better Spring Boot lifecycle control
                        sh """
                        mvn spring-boot:run \
                        -Dspring-boot.run.arguments=--server.port=${APP_PORT} \
                        -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=test" &
                        """
                    }
                }

                stage('Run Tests') {
                    steps {
                        script {
                            // DEVIATION: Health check instead of sleep 30 because:
                            // 1. More reliable than arbitrary wait
                            // 2. Standard CI/CD practice
                            waitUntil {
                                try {
                                    def code = sh(
                                        script: "curl -s -o /dev/null -w '%{http_code}' '${HEALTH_CHECK_URL}'",
                                        returnStdout: true
                                    ).trim()
                                    
                                    echo "Response code: ${code}"
                                    return (code == '200')
                                } catch (Exception e) {
                                    sleep(5)
                                    return false
                                }
                            }
                            sh 'mvn -Dtest=**/RestIT test'
                        }
                    }
                }
            }
            post {
                always {
                    sh 'pkill -f "spring-boot:run" || true'
                }
            }
        }
    }
}
