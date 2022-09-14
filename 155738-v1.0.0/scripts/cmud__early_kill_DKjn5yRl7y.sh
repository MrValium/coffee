#!/bin/bash

while [[ ( ! -f "logfile.txt" ) || ( $(/bin/grep -E 'java\.io\.FileNotFoundException:.+\(Too many open files\)' "logfile.txt" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"