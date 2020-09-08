FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY out/artifacts/bookStore_DB2_jar /app
#ADD target/demo-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app/bookStore_DB2.jar"]
