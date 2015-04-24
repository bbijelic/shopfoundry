#!/bin/bash

# Location of bin directory
BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )";
PID_FILE="$BIN_DIR/service.pid";

# check pid file
if [ -f "$PID_FILE" ] ; then
    echo "Pid file exists, path: "$PID_FILE", pid: $( cat "$PID_FILE" )"
    exit 1
fi

# startup
nohup java -jar $BIN_DIR/../shopfoundry-services-pki-ca-0.0.1.jar > /dev/null 2>&1 &
PID="$!"
echo "$PID" > "$PID_FILE"
echo "Started service, PID: $PID"
