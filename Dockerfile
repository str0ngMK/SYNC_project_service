# Start with a base image containing Java runtime
FROM openjdk:17-slim

# Set the working directory in the image to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Set environment variables for Gradle
ENV GRADLE_HOME /root/gradle-8.8-bin.zip
ENV GRADLE_VERSION 8.8
ENV PATH $PATH:$GRADLE_HOME/bin

# Install Gradle
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip -d /root /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    ln -s /root/gradle-${GRADLE_VERSION} /root/gradle && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip

# Give execution rights on the gradlew file
RUN chmod +x ./gradlew

# Expose port 8090 to the outside world
EXPOSE 8090

# Run the application
CMD ["./gradlew", "bootRun"]