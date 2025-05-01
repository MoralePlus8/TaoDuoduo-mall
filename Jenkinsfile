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
                        cp "taoduoduo-mall-user-service/taoduoduo-mall-user-web/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-user-service.jar"
                        cp "taoduoduo-mall-goods-service/taoduoduo-mall-goods-web/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-goods-service.jar"
                        cp "taoduoduo-mall-order-service/taoduoduo-mall-order-web/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-order-service.jar"
                        cp "taoduoduo-mall-recommend-service/taoduoduo-mall-recommend-web/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-recommend-service.jar"
                        cp "taoduoduo-mall-shop-cart-service/taoduoduo-mall-shop-cart-web/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-shop-cart-service.jar"
                        cp "taoduoduo-mall-gateway-admin/target/*.jar" "${DEPLOY_DIR}/taoduoduo-mall-gateway-admin.jar"
                    """
                }
            }
        }

//         stage('Restart Services') {
//             steps {
//                 script {
//                     def services = getMicroserviceModules()
//                     services.each { service ->
//                         restartService(service)
//                     }
//                 }
//             }
//         }
    }
}

// 获取所有微服务模块
def getMicroserviceModules() {
    def modules = []
    def pomContent = readFile('pom.xml')

    // 简单解析模块名 - 不创建Matcher对象
    def moduleStart = '<module>'
    def moduleEnd = '</module>'
    int index = 0

    while ((index = pomContent.indexOf(moduleStart, index)) != -1) {
        def endIndex = pomContent.indexOf(moduleEnd, index)
        if (endIndex == -1) break

        def module = pomContent.substring(index + moduleStart.length(), endIndex).trim()
        if (fileExists("${module}/${}/src/main/resources/application.properties") ||
            fileExists("${module}/src/main/resources/application.properties")) {
            sh "echo \"Found module: ${module}\""
            modules.add(module)
        }

        index = endIndex + moduleEnd.length()
    }

    if (modules.isEmpty() &&
        (fileExists("src/main/resources/application.yml") ||
         fileExists("src/main/resources/application.properties"))) {
        modules.add('.')
    }

    return modules
}

// 部署到本地目录
def deployToLocal(String serviceName) {
    // 源jar文件路径
    def sourceJar = serviceName == '.' ? "target/*.jar" : "${serviceName}/target/*.jar"
    // 目标文件名
    def targetJar = "${serviceName}.jar"

    echo "正在部署服务 ${serviceName} 到本地目录 ${DEPLOY_DIR}"

    // 复制jar文件到部署目录
    sh """
        cp ${sourceJar} ${DEPLOY_DIR}/${targetJar}
    """

    echo "服务 ${serviceName} 已部署到 ${DEPLOY_DIR}/${targetJar}"
}

// 重启服务
def restartService(String serviceName) {
    echo "正在重启服务 ${serviceName}"

    // 使用sudo执行systemctl命令
    sh "sudo systemctl restart ${serviceName}.service"

    // 等待服务启动
    def maxAttempts = 10
    def waitTime = 5 // seconds

    sh """
        for i in \$(seq 1 ${maxAttempts}); do
            if systemctl is-active --quiet ${serviceName}.service; then
                echo "服务 ${serviceName} 已成功启动"
                exit 0
            fi
            echo "等待服务启动(尝试 \$i/${maxAttempts})..."
            sleep ${waitTime}
        done
        echo "服务 ${serviceName} 启动失败"
        exit 1
    """
}