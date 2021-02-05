#!/bin/sh
tpid=`cat tpid|awk '{print $1}'`
tpid=`ps -aef|grep $tpid|awk '{print $2}'|grep $tpid`
if [ ${tpid} ]
then
  echo App is already running!!!
else
rm -f tpid

APPLICATION="@project.name@"
VERSION="@project.version@"

nohup java -jar ../${APPLICATION}-${VERSION}.jar --spring.config.location=../config/application.yml > /dev/null 2>&1 &

echo $! > tpid
fi
