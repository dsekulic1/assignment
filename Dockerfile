FROM eclipse-temurin:11
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
COPY mydb.db mydb.dbdocker
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
