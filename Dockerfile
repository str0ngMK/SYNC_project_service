FROM openjdk:17-slim

# wget과 unzip 설치
RUN apt-get update && apt-get install -y wget unzip

WORKDIR /app

# 소스 코드 복사
COPY . /app

# Gradle 다운로드 및 압축 해제
RUN wget https://services.gradle.org/distributions/gradle-8.8-bin.zip -P /app
RUN unzip /app/gradle-8.8-bin.zip -d /app

# Set the executable permission for the Gradle wrapper
RUN chmod +x ./gradlew

# Continue with the build process
RUN ./gradlew --no-daemon wrapper
