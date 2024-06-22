# Start with a base image containing Java runtime
FROM openjdk:17-slim

# Set the working directory in the image to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Set environment variables for Gradle
ENV GRADLE_HOME /root/gradle
ENV GRADLE_VERSION 8.8
ENV PATH $PATH:$GRADLE_HOME/bin

# Copy the pre-downloaded Gradle distribution into the Docker image
COPY /root/gradle-${GRADLE_VERSION}-bin.zip /tmp

# Install Gradle
RUN apt-get update && \
    apt-get install -y unzip && \
    unzip -d /root /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    ln -s /root/gradle-${GRADLE_VERSION} /root/gradle && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip

# Give execution rights on the gradlew file
RUN chmod +x ./gradlew

# Expose port 8090 to the outside world
EXPOSE 8090

# Run the application
CMD ["./gradlew", "bootRun"]