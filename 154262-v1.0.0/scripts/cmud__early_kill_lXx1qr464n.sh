#!/bin/bash

while [[ ( ! -f "mud.log" ) || ( $(/bin/grep "java.lang.IllegalArgumentException: Not a valid character" "mud.log" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"