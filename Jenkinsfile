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
                    // 获取所有微服务模块
                    def services = getMicroserviceModules()

                    // 确保部署目录存在
                    sh "sudo mkdir -p ${DEPLOY_DIR}"
                    sh "sudo chown -R jenkins:jenkins ${DEPLOY_DIR}"

                    // 部署每个服务
                    services.each { service ->
                        deployToLocal(service)
                    }
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

    // 方法1：解析pom.xml文件内容
    def pomContent = readFile('pom.xml')
    def matcher = pomContent =~ /<module>(.*?)<\/module>/

    matcher.each { match ->
        def module = match[1]
        if (fileExists("${module}/src/main/resources/application.yml") ||
            fileExists("${module}/src/main/resources/application.properties")) {
            modules.add(module)
        }
    }

    // 如果是单模块项目
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
        chmod 755 ${DEPLOY_DIR}/${targetJar}
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