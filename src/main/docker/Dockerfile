#  镜像构建参考文档  http://lavecoral.me/archives/215.html
FROM adoptopenjdk/openjdk8-openj9:alpine-slim
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /tmp
RUN mkdir /app

COPY /*.zip /app/
cd /app
unzip sprinbootes-0.0.1-SNAPSHOT-assembly
ENTRYPOINT ["cd /app/sprinbootes-0.0.1-SNAPSHOT-assembly/sprinbootes-0.0.1-SNAPSHOT/bin && startup.sh"]
