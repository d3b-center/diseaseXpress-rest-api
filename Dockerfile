FROM hseeberger/scala-sbt:8u131-jdk_2.12.2_0.13.15 as build

COPY . /diseaseXpress

WORKDIR /diseaseXpress

RUN sbt assembly

FROM openjdk:8-jre

COPY --from=build /diseaseXpress/target/scala-2.11/disease-express-rest-api-assembly-*.jar /app.jar

CMD java -Dhttp.port=80 ${JAVA_OPTS} -jar /app.jar
