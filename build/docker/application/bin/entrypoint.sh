#!/usr/bin/env bash

set -eo pipefail

# Expects the following environment variables to exist:
#
# CATALINA_HOME - Tomcat directory
# APP_ROOT - Application directory

while [ $# -gt 0 ]; do
  case "$1" in
    --context-path=*)             # required
      CONTEXT_PATH="${1#*=}"
      ;;
    --catalog-url=*)             # required, example: http://test.jcatalog.com/dev-proc/opc/oci/index
      CATALOG_URL="${1#*=}"
      ;;
    --public-url=*)             # required, example: http://test.jcatalog.com/dev-proc/oci-test
      PUBLIC_URL="${1#*=}"
      ;;
    --java-opts=*)               # optional - additional Java options
      EXTRA_JAVA_OPTS="${1#*=}"
      ;;
    *)
      printf "Error: Invalid argument ${1}\n"
      exit 1
  esac
  shift
done

SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

. $SCRIPT_DIR/assert-vars.sh CONTEXT_PATH CATALOG_URL PUBLIC_URL

# link app to context path
# Make /a/b/c -> a#b#c
ESCAPED_CONTEXT_PATH=${CONTEXT_PATH#*/}
ESCAPED_CONTEXT_PATH="${ESCAPED_CONTEXT_PATH//\//#}"
ln -s "$APP_ROOT" "$CATALINA_HOME/webapps/$ESCAPED_CONTEXT_PATH"

printf "\nwebapps:\n"
ls -la "$CATALINA_HOME/webapps"

# prepare configuration

for cfg_file_path in $APP_ROOT/WEB-INF/conf/opc/default/*
do
  cat << EOF > $APP_ROOT/WEB-INF/conf/opc/user/$(basename $cfg_file_path)
HOOK_URL=${PUBLIC_URL}/inbound
catalog_url=${CATALOG_URL}
EOF
done

ls -l $APP_ROOT/WEB-INF/conf/opc/user

#####################################################################
#
# Run Tomcat
#
#####################################################################

COMMON_JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xms2g -Xmx2g -XX:MaxMetaspaceSize=1024m -XX:ReservedCodeCacheSize=256m -XX:+UseCodeCacheFlushing -Djavax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -XX:+UseG1GC"

# Make sure to pass $JAVA_OPTS in the end to make it rewriteable
JAVA_OPTS=`printf "%s %s %s" "$COMMON_JAVA_OPTS" "$JOSSO_JAVA_OPTS" "$EXTRA_JAVA_OPTS"`
printf "JAVA_OPTS: $JAVA_OPTS\n"
export JAVA_OPTS

exec catalina run
