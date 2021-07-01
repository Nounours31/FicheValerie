@echo off

set prereq=E:\WS\GitHubPerso\FicheValerie\server\v1.0.0\prereqGradle\jars
rm %prereq%\*.jar

SETLOCAL EnableDelayedExpansion
for /f "tokens=*" %%G in ('where /r C:\Users\pfs\.gradle\caches\modules-2\files-2.1 *.jar') do (
    xcopy  %%G %prereq% /E /Y /V /C /F
)

echo OK Copy Prereqs