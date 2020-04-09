FROM hseeberger/scala-sbt:8u131-jdk_2.12.2_0.13.15 as build
COPY . /diseaseXpress
WORKDIR /diseaseXpress
RUN sbt assembly
FROM openjdk:8-jre
COPY --from=build /diseaseXpress/target/scala-2.11/disease-express-rest-api-assembly-*.jar /app.jar
CMD java -Dhttp.port=80 ${JAVA_OPTS} -jar /app.jar

RUN apt install build-essential zlib1g-dev libncurses5-dev libgdbm-dev libnss3-dev libssl-dev libsqlite3-dev libreadline-dev libffi-dev curl
RUN curl -O https://www.python.org/ftp/python/3.8.2/Python-3.8.2.tar.xz
RUN tar -xf Python-3.8.2.tar.xz
RUN ./Python-3.8.2/configure --enable-optimizations
RUN make -j 2
RUN make altinstall
RUN cp /usr/local/bin/python3.8 /usr/local/bin/python