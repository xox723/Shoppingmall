# 使用官方的 OpenJDK 17 基礎映像
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製應用程式的 jar 檔到容器中


COPY DockerProdect1.jar /app/my-application.jar


# 暴露應用程式的運行端口（假設您的應用程式在 8080 端口運行）
EXPOSE 8080

# 設定啟動容器時執行的指令，運行 jar 檔
ENTRYPOINT ["java", "-jar", "/app/my-application.jar"]