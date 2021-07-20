@echo on

echo "To install: gradle installDist --warning-mode all --info"
echo "Use: --warning-mode all --info"

for /f "skip=1 delims=" %%A in (
  'wmic computersystem get name'
) do for /f "delims=" %%B in ("%%A") do set "compName=%%A"


set Server=%compName%

set Server=%Server: =%
echo "Server: >%Server%<"

set JAVA_HOME=
set GRADDLE_HOME=

if /i "%Server%" equ "dell2259dsy" (
	set JAVA_HOME=C:\java\jdk1.8.0_251
	set GRADDLE_HOME=E:\programfiles\gradle\gradle-7.1.1
)

if /i "%Server%" equ "lp5-pfs-dsy" (
	set JAVA_HOME=C:\java\jdk1.8.0_241
	set GRADDLE_HOME=E:\ProgramFiles\gradle-7.1.1
)

echo JAVA_HOME: %JAVA_HOME%

if not defined JAVA_HOME (
	echo "ERROR"
	goto :fin
) 
echo "Update path"
echo "set ..."
set PATH=%JAVA_HOME%;%JAVA_HOME%\bin;%GRADDLE_HOME%;%GRADDLE_HOME%\bin;%PATH%
echo "Updated"


:fin