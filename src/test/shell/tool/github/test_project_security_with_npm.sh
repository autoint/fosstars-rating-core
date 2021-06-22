#!/bin/bash

TOKEN_OPTION=""
if [ "$TOKEN" != "" ]; then
  TOKEN_OPTION="--token $TOKEN"
fi

JAVA="java"
if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
fi

JAR=${JAR:-"target/fosstars-github-rating-calc.jar"}

source lib.sh

clean_cache

$JAVA -jar $JAR \
  --npm jquery --verbose \
  $TOKEN_OPTION > tmp.log 2>&1

if [ $? != 0 ]; then
  cat tmp.log
  echo "Unexpected exit code"
  exit 1
fi

cat tmp.log

check_expected_output "${project_security_default_expected_strings[@]}" | tee | grep Failed
if [ $? == 0 ]; then
  echo "check_expected_output() failed"
  exit 1
fi

declare -a expected_strings=(
  'https://github.com/jquery/jquery'
)

check_expected_output "${expected_strings[@]}" | tee | grep Failed
if [ $? == 0 ]; then
  echo "check_expected_output() failed"
  exit 1
fi

if [ grep Exception tmp.log > /dev/null 2>&1 ]; then
  echo "Exceptions found"
  exit 1
fi
