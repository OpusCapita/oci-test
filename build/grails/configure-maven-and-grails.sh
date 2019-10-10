#!/usr/bin/env bash

set -eo pipefail

echo "[INFO] Configure maven and grails"

if [ ! -f /.dockerenv ]; then
  echo "[WARNING] Skipping 'configure-maven-and-grails.sh' execution, it will be run only inside Docker container because it can override local user's settings"
fi

if [ -f /.dockerenv ]; then
  # Get values required by ~/.m2/settings.xml and ~/.grails/settings.groovy
  MAVEN_REPO=$( vault kv get -field=value machineuser/MAVEN_REPO )
  JFROG_USER=$( vault kv get -field=value machineuser/JFROG_USER )
  JFROG_PASSWD=$( vault kv get -field=value machineuser/JFROG_PASSWD )

  export MAVEN_REPO=$MAVEN_REPO
  export JFROG_USER=$JFROG_USER
  export JFROG_PASSWD=$JFROG_PASSWD
fi
