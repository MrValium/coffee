#!/bin/bash

while [[ ( ! -f "logfile.txt" ) || ( $(/bin/grep -E 'java\.lang\.ArrayIndexOutOfBoundsException:\s+-?\d+' "logfile.txt" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"