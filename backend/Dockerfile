# First stage: Maven build
FROM maven as build
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY . .

#ENTRYPOINT ["mvn", "spring-boot:start"]

# Run Maven build
RUN mvn clean compile assembly:single

# Second stage: Application runtime
FROM openjdk:21-jdk

# Copy the built artifact from the first stage
#COPY --from=build /app/target /app
COPY --from=build /app/target/*.jar /app/backend-0.0.1-SNAPSHOT-jar-with-dependencies.jar
WORKDIR /app
# Set the entrypoint to run your application
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
