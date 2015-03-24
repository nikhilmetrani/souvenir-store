@echo off
cd /d %~dp0
echo Setting up environment...
call setenv.bat
if not exist classes md classes
echo Attempting to compile SouvenirStore Application
javac -d classes -sourcepath src -cp src\sg\edu\nus\iss\se23pt2\pos\*.java 
javac -d classes -sourcepath test -cp lib\hamcrest-core-1.3.jar;lib\junit-4.12.jar;classes;test; test\sg\edu\nus\iss\se23pt2\pos\*.java test\sg\edu\nus\iss\se23pt2\pos\datastore\*.java
pause