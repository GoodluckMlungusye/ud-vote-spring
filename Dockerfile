FROM openjdk:23-jdk-slim
WORKDIR /ud_vote_spring_app
COPY target/*.jar ud_vote.jar
COPY wait-for-it.sh /ud_vote_spring_app/wait-for-it.sh
RUN chmod +x /ud_vote_spring_app/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["java","-jar","ud_vote.jar"]