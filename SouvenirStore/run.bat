@echo off
cd /d %~dp0
echo Setting up environment...
call setenv.bat
echo Attempting to run sg.edu.nus.iss.se23pt2.pos.StoreApplication
java -classpath bin sg.edu.nus.iss.se23pt2.pos.StoreApplication %*
