#!/bin/bash

while [[ ( ! -f "$SS_TC_ROOT/logs/execute/execute.stderr" ) || ( $(/bin/grep "java.lang.NullPointerException" "$SS_TC_ROOT/logs/execute/execute.stderr" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"