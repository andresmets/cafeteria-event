FROM postgres
ENV POSTGRES_PASSWORD postgres


FROM openjdk:18

EXPOSE 8080

RUN mkdir /app
RUN mkdir /pgdata

COPY build/libs/cafeteria-event-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-DLOG_FILE=/app/logs/application.log","-jar","/app/spring-boot-application.jar"]