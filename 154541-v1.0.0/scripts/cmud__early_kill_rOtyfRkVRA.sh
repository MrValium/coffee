#!/bin/bash

while [[ ( ! -f "logfile.txt" ) || ( $(/bin/grep "The thread startup raised an exception.  This should never happen." "logfile.txt" | wc -l) -eq 0 ) ]];  do sleep 5; done; killtree.sh -d -f "$SS_TC_ROOT/rc_parent.pid"