pipeline {
    agent any

	tools {
    	maven 'Maven'
	}
    
    environment {
        REGISTRY = "registry.hub.docker.com" // e.g., Docker Hub or any other registry
        SONAR_HOST_URL = 'https://sonarqube.flexsolution.xyz'
        SONARQUBE_TOKEN = credentials('SonarQube')
    }

    stages {
	stage ('Mensaje Inicial Slack') {
		steps {
			slackSend channel: "#proyecto-sismos-pipeline", color: "good", message: "Inicio de Build"
		}
	}
        stage('Checkout') {
            steps {
                // Clona el repositorio
                git branch: 'main', url: 'https://github.com/digivoro/g5-mvc'
            }
        }
        stage('Build') {
            steps {
                // Construye el proyecto Maven
                sh 'mvn clean package'
            }
        }

/*
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Realiza el análisis de SonarQube
                    def scannerHome = tool 'sonarqube' // Nombre de la herramienta SonarQube Scanner configurada en Jenkins
                    withSonarQubeEnv('sonarqube') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=spring-boot-complete -Dsonar.maven.scanAll=true -Dsonar.java.binaries=. -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.token=$SONARQUBE_TOKEN"
                    }
                }
            }
        }
        stage('Quality Gate') {
            steps {
                script {
                    // Espera y comprueba el resultado del análisis de SonarQube
                    timeout(time: 1, unit: 'MINUTES') {
                        def qg = waitForQualityGate(credentialsId: SONARQUBE_TOKEN)
              			if (qg.status != 'OK') {
                  			error "Pipeline aborted due to quality gate failure: ${qg.status}"
              			}
                    }
                }
            }
        }
*/

        stage('Cleanup') {
            steps {
                // Limpieza despues de cada build
                cleanWs()
            }
        }
    }

	post {
		success {
			slackSend  channel: "#proyecto-sismos-pipeline", color: "good", message: "Finalización de Build"
		}
		failure {
			slackSend  channel: "#proyecto-sismos-pipeline", color: "danger", message: "Error en el Build"
		}
	}
}
