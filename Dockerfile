FROM openjdk:19 AS JAVA_19
FROM gradle AS BUILD_STAGE
ENV APP_HOME=/usr/app

RUN mkdir $APP_HOME

WORKDIR $APP_HOME

COPY . $APP_HOME
RUN gradle clean build

ENV EXECUTABLE=$APP_HOME/build/libs/cafeteria-event.jar
RUN java --version
ENTRYPOINT java -jar $EXECUTABLE

EXPOSE 8080

