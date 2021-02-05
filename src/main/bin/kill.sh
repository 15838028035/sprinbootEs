#!/bin/sh

APPLICATION="@project.name@"
VERSION="@project.version@"

kill -9 `ps -ef | grep ${APPLICATION}-${VERSION}.jar | awk '{print $2}'|awk 'NR==1'`
