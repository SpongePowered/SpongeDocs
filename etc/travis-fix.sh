#!/bin/bash
MESSAGE_DELAY=5m

C='\u2588';B='\e[90m';A='\u2592';D='\e[33m'
while :; do
    sleep $MESSAGE_DELAY; echo -e "\n$D$D$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C\n$D$D$C$C$C$C$C$C$A$A$C$C$A$A$A$A$A$A$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$D$D$A$A$C$C$C$C$C$C$A$A$C$C$C$C$C$C$C$C$C$C$C$C$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$D$D$A$A$C$C$C$C$C$C$A$A$C$C$C$C$C$C$C$C$C$C$C$C$C$C\n$D$D$C$C$C$C$C$C$A$A$C$C$C$C$C$C$C$C$A$A$C$C$C$C$C$C$C$C$C$C$C$C$C$C\n$D$D$C$C$C$C$C$C$C$C$A$A$A$A$B$B$A$A$A$A$A$A$D$D$A$A$A$A$A$A$A$A$C$C$C$C$C$C\n$D$D$C$C$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C$C$C\n$D$D$C$C$C$C$C$C$A$A$B$B$A$A$D$D$C$C$B$B$A$A$A$A$A$A$A$A$D$D$C$C$B$B$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$C$C$A$A$B$B$A$A$D$D$C$C$B$B$A$A$A$A$A$A$A$A$D$D$C$C$B$B$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$C$C$A$A$B$B$A$A$D$D$C$C$B$B$A$A$A$A$A$A$A$A$D$D$C$C$B$B$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$D$D$C$C$C$C$B$B$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$D$D$C$C$C$C$B$B$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$D$D$C$C$C$C$B$B$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$D$D$C$C$C$C$B$B$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C\n$D$D$C$C$C$C$A$A$B$B$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$A$D$D$A$A$C$C"
done &
PID=$!

if [[ $BRANCHNAME = master ]]; then
     crowdin-cli upload sources
     crowdin-cli download

else
     crowdin-cli upload sources -b $BRANCHNAME
     crowdin-cli download -b $BRANCHNAME
fi

kill $PID
