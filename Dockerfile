# Stage 1: Build the JAR
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Final image
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /ud_vote_spring_app

# Copy the JAR from builder stage
COPY --from=builder /app/target/*.jar ud_vote.jar

# Optional helper script
COPY wait-for-it.sh /ud_vote_spring_app/wait-for-it.sh
RUN chmod +x /ud_vote_spring_app/wait-for-it.sh

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "ud_vote.jar"]