@echo off
%~d0
cd %~dp0
call mvn clean
call mvn deploy
pause