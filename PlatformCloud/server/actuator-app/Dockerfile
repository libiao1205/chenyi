FROM java:8
VOLUME /tmp
ADD target/actuator-app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
