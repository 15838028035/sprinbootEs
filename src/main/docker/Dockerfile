#  镜像构建参考文档  http://lavecoral.me/archives/215.html
FROM adoptopenjdk/openjdk8-openj9:alpine-slim
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /tmp
RUN mkdir app
RUN mkdir -p /app/config
RUN mkdir -p /app/lib

COPY target/lib/* /app/lib
COPY config/* /app/config/
ADD sprinbootEs-0.0.1-SNAPSHOT.jar /app/sprinbootEs-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xshareclasses","-Xquickstart","-jar","/app/sprinbootEs-0.0.1-SNAPSHOT.jar"]
