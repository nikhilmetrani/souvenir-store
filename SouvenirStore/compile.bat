@echo off
cd /d %~dp0
echo Setting up environment...
call setenv.bat
if not exist bin md bin
echo Attempting to compile sg.edu.nus.iss.se23pt2.pos.StoreApplication
javac -d bin -sourcepath src -cp src\sg\edu\nus\iss\se23pt2\pos\*.java
