FROM openjdk:17-slim
ADD /target/code-demo-persion-0.0.1-SNAPSHOT.jar  /demo.jar
EXPOSE 8088
ENTRYPOINT ["java","-Xms1024m","-Xmx1024m","-jar","-Duser.timezone=GMT+08","/demo.jar","--spring.profiles.active=dev","-c"]