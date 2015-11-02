@echo off
cd ./bins
:loop
devcon find "USB\VID_0955&PID_7820"
ping 127.0.0.1 -n 2 > nul
goto loop