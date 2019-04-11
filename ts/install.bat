@echo off
cls
SET SINGLESESSION=1
SET BLANK=1
IF /I *%1 == *MULTI SET SINGLESESSION=0
IF /I *%2 == *MULTI SET SINGLESESSION=0
IF /I *%1 == *BLANK SET BLANK=0
IF /I *%2 == *BLANK SET BLANK=0
SET SOURCEFOLDER=%~dp0
ECHO Source Folder is %SOURCEFOLDER%
echo Taking ownership of %SystemRoot%\System32\termsrv.dll
takeown /a /f %SystemRoot%\System32\termsrv.dll
echo Granting Administrators rights
icacls %SystemRoot%\System32\termsrv.dll /Grant Администраторы:F
echo Stopping Terminal Services
net stop TermService
copy %SystemRoot%\System32\termsrv.dll %SystemRoot%\System32\*.*.bak
echo Copying %SOURCEFOLDER%termsrv.dll to %SystemRoot%\System32\
copy /y "%SOURCEFOLDER%termsrv.dll" %SystemRoot%\System32\
echo Starting Terminal Services
net start TermService
ECHO Pausing 5 seconds to give service time to start listening
choice /n /c y /d y /t 5 > nul
echo Checking if Service is listening on port 3389
echo command SUBST deleted in this string
netstat -a | find /i "3389"
if ERRORLEVEL 1 goto SERVICENOTLISTENING
echo Service is listening
goto CONTINUE
:SERVICENOTLISTENING
echo Service is not listening
:CONTINUE
echo Done
Pause