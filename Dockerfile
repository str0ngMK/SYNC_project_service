FROM openjdk:17-slim
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew  # gradlew 파일에 실행 권한 추가
EXPOSE 8090
CMD ["./gradlew", "bootRun"]
