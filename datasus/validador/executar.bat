@echo off

set CLASSPATH=  
call :setall . .\libs

java -cp %CLASSPATH% ui.FhirValidacaoWindow

goto end
  
:setall
if .%1.==.. goto end
set dir=%1
set dir=%dir:"=%
if not "%CLASSPATH%"=="" set CLASSPATH=%CLASSPATH%;%dir%
if "%CLASSPATH%"=="" set CLASSPATH=%dir%
for %%i in ("%dir%\*.jar") do call :setone "%%i"
shift
goto setall
  
:setone
set file=%1
set file=%file:"=%
set CLASSPATH=%CLASSPATH%;%file%

:end

