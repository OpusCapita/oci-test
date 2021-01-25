#!/usr/bin/env bash

set -eo pipefail

# Expects the following environment variables to exist:
#
# CATALOG_URL - Online product catalog URL
# PUBLIC_URL - Oci test public URL

while [ $# -gt 0 ]; do
  case "$1" in
    --catalog-url=*)             # required, example: http://test.jcatalog.com/dev-proc/opc
      CATALOG_URL="${1#*=}"
      ;;
    --public-url=*)             # required, example: http://test.jcatalog.com/dev-proc/oci-test
      PUBLIC_URL="${1#*=}"
      ;;
    *)
      printf "Error: Invalid argument ${1}\n"
      exit 1
  esac
  shift
done

cat << EOF > "config.json"
{
  "public_oci_test_url": "${PUBLIC_URL}",
  "public_opc_url": "${CATALOG_URL}"
}
EOF

npm start
