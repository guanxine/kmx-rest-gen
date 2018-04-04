#!/bin/bash
SCRIPT_DIR=$(dirname $(readlink -e $0))
. ${SCRIPT_DIR}/image

set -ex

export MAVEN_OPTS="-Xms128m -Xmx4096m -Xss128m -XX:MaxPermSize=1024M -XX:+CMSClassUnloadingEnabled"

# maven build
# mvn -f $SCRIPT_DIR/../../pom.xml -pl k2platform-file-parent/k2platform-file-rest -am clean package -Dmaven.test.skip=false
mvn clean package -Dmaven.test.skip=false