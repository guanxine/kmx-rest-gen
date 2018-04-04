FROM tomcat:7.0
RUN apt-get update
RUN apt-get install -y vim
ADD ./tomcat_conf/server.xml /usr/local/tomcat/conf/
RUN mkdir -p /usr/local/tomcat/conf/Catalina/localhost
ADD ./target/k2platform-rest-demo.war /usr/local/tomcat/webapps/rest-demo.war
ADD docker/entrypoint.sh /opt/k2data/entrypoint.sh

ENTRYPOINT ["/opt/k2data/entrypoint.sh"]
CMD []

ARG branch
ARG commit
ARG buildtime
ARG owner
LABEL branch=$branch \
        commit=$commit \
        buildtime=$buildtime \
        owner=$owner