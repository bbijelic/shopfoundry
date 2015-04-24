#!/bin/bash
# Location of bin directory
BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )";
PID_FILE="$BIN_DIR/service.pid";

# check pid file
if [ ! -f "$PID_FILE" ] ; then
    echo "PID file doesn't exist, path: $PID_FILE"
    exit 1
fi

# shutdown
PID="$( cat "$PID_FILE" )"
kill "$PID"
rm -f "$PID_FILE"
echo "Stopping service, PID: $PID"
