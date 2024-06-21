FROM openjdk:17-slim

# Java 환경 변수 설정
ENV JAVA_HOME /usr/local/openjdk-17
ENV PATH $PATH:$JAVA_HOME/bin

WORKDIR /app

# 현재 디렉토리의 모든 파일을 컨테이너의 /app 디렉토리에 복사
COPY . /app

# 로컬에서 다운로드한 Gradle 캐시를 Docker 이미지에 복사
COPY .gradle /root/.gradle

# gradlew 파일에 실행 권한 추가
RUN chmod +x ./gradlew

