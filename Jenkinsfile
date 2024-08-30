pipeline {
    agent any

	tools {
    	maven 'Maven'
	}
    
    environment {
        REGISTRY = "registry.hub.docker.com" // e.g., Docker Hub or any other registry
        SONAR_HOST_URL = 'https://sonarqube.flexsolution.xyz'
        SONARQUBE_TOKEN = credentials('SonarQube')
	NEXUS_URL = 'nexus.flexsolution.xyz'
        NEXUS_REPO = 'alerta-sismos-repo'
        NEXUS_CREDENTIALS_ID = 'Nexus'
	BUILD_TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"
    }

    stages {
	stage ('Mensaje Inicial Slack') {
		steps {
			slackSend channel: "#proyecto-sismos-pipeline", color: "#1919ff", message: ":hammer_and_wrench: Inicio de Build #${env.BUILD_NUMBER}"
		}
	}
        stage('Checkout') {
            steps {
                // Clona el repositorio
                git branch: 'main', url: 'https://github.com/digivoro/g5-mvc'
            }
        }
        stage('Selenium') {
            steps {
                sh 'java –cp bin:lib/* org.testng.TestNG TestNG.xml'
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
	stage('Upload Artifact to Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'https',
                    nexusUrl: "${NEXUS_URL}",
                    groupId: 'cl.devopcitos',
                    version: "${env.BUILD_NUMBER}-${env.BUILD_TIMESTAMP}",
                    repository: "${NEXUS_REPO}",
                    credentialsId: "${NEXUS_CREDENTIALS_ID}",
                    artifacts: [
                        [artifactId: 'alertasismos',
                         classifier: '',
                         file: 'target/alertasismos-0.0.1-SNAPSHOT.jar',
                         type: 'jar']
                    ]
                )
            }
        }
        stage('Cleanup') {
            steps {
                // Limpieza despues de cada build
                cleanWs()
            }
        }
    }

	post {
		success {
			slackSend  channel: "#proyecto-sismos-pipeline", color: "good", message: ":white_check_mark: Finalización de Build #${env.BUILD_NUMBER}"
		}
		failure {
			slackSend  channel: "#proyecto-sismos-pipeline", color: "danger", message: ":x: Error en el Build #${env.BUILD_NUMBER}"
		}
	}
}
