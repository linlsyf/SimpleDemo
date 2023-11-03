FROM thingsboard/openjdk11
VOLUME /tmp
ADD target/common.jar app.jar
# EXPOSE 8888 ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=prd"]

EXPOSE 1021

CMD  java -jar  app.jar
