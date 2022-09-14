#!/bin/bash

while [[ ( ! -f "$SS_TC_ROOT/logs/execute/execute.stderr" ) || ( $(/bin/grep "java.lang.IllegalArgumentException: Not a valid character" "$SS_TC_ROOT/logs/execute/execute.stderr" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"