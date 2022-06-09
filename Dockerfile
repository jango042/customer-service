FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp

EXPOSE 8004

ADD ./target/customer-service.jar /customer-service.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /customer-service.jar" ]