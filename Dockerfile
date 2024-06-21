FROM arm64v8/openjdk:17-slim

# Java 환경 변수 설정
ENV JAVA_HOME /usr/local/openjdk-17
ENV PATH $PATH:$JAVA_HOME/bin

WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew  # gradlew 파일에 실행 권한 추가

EXPOSE 8090

CMD ["./gradlew", "bootRun"]