# Dockerfile for FA WuHan
# VERSION 1.0
# Author: zhen.wang
FROM openjdk:8-jre-slim
ENV LANG C.UTF-8
ENV PATH $PATH:/usr/bin
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /novel/apps/ams-schedule/
COPY ./target/mycim-ams-schedule-admin.jar /novel/apps/ams-schedule/

RUN mkdir -p /novel/apps/ams-schedule/config/
VOLUME /novel/apps/ams-schedule/config/

EXPOSE 10702

WORKDIR /novel/apps/ams-schedule
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -server -Xms1000m -Xmx4000m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Dspring.profiles.active=dev -Dspring.config.location=./config -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/ -Djava.awt.headless=true -Dfile.encoding=UTF-8","-jar","/novel/apps/ams-schedule/mycim-ams-schedule-admin.jar"]
