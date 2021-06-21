FROM java:8
VOLUME /tmp
ADD target/main-app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
