FROM openjdk:17-slim

# Set working directory
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Download the Gradle wrapper manually
RUN wget https://services.gradle.org/distributions/gradle-8.8-bin.zip -P /app
RUN unzip /app/gradle-8.8-bin.zip -d /app

# Set the executable permission for the Gradle wrapper
RUN chmod +x ./gradlew

# Continue with the build process
RUN ./gradlew --no-daemon wrapper
