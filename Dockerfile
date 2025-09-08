FROM amazoncorretto:21
LABEL maintainer="Tomasz Fehrenbacher tomasz.fehrenbacher@gmx.de"
COPY target/qr-server.jar /srv/app.jar
WORKDIR /srv
EXPOSE 80 8080
ENTRYPOINT ["java","-jar","app.jar"]
