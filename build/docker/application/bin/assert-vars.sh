#!/usr/bin/env bash

set -eo pipefail

if [ $# -eq "0" ]; then
  printf "Assert if variables are set in scope\n"
  printf "Usage: $(basename $0) GH_NAME GH_PASS\n"
  exit 1
fi

for v in $@; do
  # if one of required variables is empty
  if [[ -z "${!v}" ]]; then
    printf "$(basename $0) ERROR: $v is empty\n"
    exit 1
  fi
done

printf "ok\n"
