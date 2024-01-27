FROM amazoncorretto:17
MAINTAINER Tomasz Fehrenbacher <admin@famifeh.de>
COPY target/qr-server.jar /srv/app.jar
WORKDIR /srv
EXPOSE 80 8080
ENTRYPOINT ["java","-jar","app.jar"]
