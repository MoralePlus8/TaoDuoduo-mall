pipeline {
    agent any

    tools {
        jdk 'jdk8'
        maven 'M3'
    }

    environment {
        DEPLOY_DIR = '/root/java/tdd'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/MoralePlus8/TaoDuoduo-mall',
                credentialsId: 'fc7870fc-6e71-424e-957e-6e11b1ef8f92'
            }
        }

        stage('Build All Services') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean package -DskipTests"
            }
        }

        stage('Deploy to Local') {
            steps {
                script {
                    // 确保部署目录存在
                    sh "mkdir -p ${DEPLOY_DIR}"
                    sh "chown -R jenkins:jenkins ${DEPLOY_DIR}"

                    sh """
                        cp "taoduoduo-mall-user-service/taoduoduo-mall-user-web/target/taoduoduo-mall-user-web-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-user-service.jar"
                        cp "taoduoduo-mall-goods-service/taoduoduo-mall-goods-web/target/taoduoduo-mall-goods-web-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-goods-service.jar"
                        cp "taoduoduo-mall-order-service/taoduoduo-mall-order-web/target/taoduoduo-mall-order-web-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-order-service.jar"
                        cp "taoduoduo-mall-recommend-service/taoduoduo-mall-recommend-web/target/taoduoduo-mall-recommend-web-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-recommend-service.jar"
                        cp "taoduoduo-mall-shop-cart-service/taoduoduo-mall-shop-cart-web/target/taoduoduo-mall-shop-cart-web-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-shop-cart-service.jar"
                        cp "taoduoduo-mall-gateway-admin/target/taoduoduo-mall-gateway-admin-0.0.1-SNAPSHOT.jar" "${DEPLOY_DIR}/taoduoduo-mall-gateway-admin.jar"
                    """
                }
            }
        }

        stage('Restart Services') {
            steps {
                script {
                    sh """
                        systemctl restart taoduoduo-mall-gateway-admin.service
                        systemctl restart taoduoduo-mall-user-service.service
                        systemctl restart taoduoduo-mall-goods-service.service
                        systemctl restart taoduoduo-mall-order-service.service
                        systemctl restart taoduoduo-mall-recommend-service.service
                        systemctl restart taoduoduo-mall-shop-cart-service.service
                    """
                }
            }
        }
    }
}
