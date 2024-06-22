# 베이스 이미지 선택
FROM openjdk:11-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# unzip 패키지 설치
RUN apt-get update && apt-get install -y unzip

# 빌드 인자 선언
ARG GRADLE_ZIP

# 빌드 인자를 사용하여 로컬의 gradle-8.8-bin.zip 파일을 컨테이너로 복사
COPY ${GRADLE_ZIP} /tmp/gradle-8.8-bin.zip

# Gradle 설치
RUN unzip /tmp/gradle-8.8-bin.zip -d /opt/gradle && \
    rm /tmp/gradle-8.8-bin.zip && \
    ln -s /opt/gradle/gradle-8.8/bin/gradle /usr/bin/gradle

# 프로젝트 파일 복사
COPY . /app

# Gradle 빌드 실행
RUN gradle build

# 엔트리포인트 설정
ENTRYPOINT ["gradle"]

# 필요한 경우 추가 명령어 실행
CMD ["--version"]
