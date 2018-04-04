#!/bin/sh


echo "start tomcat..."

mkdir /usr/local/tomcat/webapps/rest-demo
unzip /usr/local/tomcat/webapps/rest-demo.war -d /usr/local/tomcat/webapps/rest-demo

logfile=/usr/local/tomcat/webapps/rest-demo/WEB-INF/classes/log4j.properties

if [ -n ${LOG_LEVEL} ]; then
    echo "use log level: ${LOG_LEVEL}"
    sed -i "s#\${LOG_LEVEL}#${LOG_LEVEL}#g" $logfile
else
    echo "use log level: ${LOG_LEVEL}"
    sed -i "s#\${LOG_LEVEL}#info#g" $logfile
      sed -i "s#\${AUDIT_INTERVAL_SECONDS}#20#g" $base_dir/config/k2.properties
fi

if [ -n ${LOG_FILE_NAME} ]; then
    echo "use log file name: ${LOG_FILE_NAME}"
    sed -i "s#\${LOG_FILE_NAME}#${LOG_FILE_NAME}#g" $logfile
else
    echo "use log file name: ${LOG_FILE_NAME}"
    sed -i "s#\${LOG_FILE_NAME}#file-rest#g" $logfile
fi

sh /usr/local/tomcat/bin/startup.sh
tail -f /dev/null &
wait