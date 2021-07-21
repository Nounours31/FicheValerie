@echo on

call rm -rf E:\WS\GitHubPerso\FicheValerie\tomee-8.0.6\apache-tomee-plus-8.0.6\webapps\fiche*.war
call gradle war

pause
call cp E:\WS\GitHubPerso\FicheValerie\server\v1.0.0\lib\build\libs\*.war E:\WS\GitHubPerso\FicheValerie\tomee-8.0.6\apache-tomee-plus-8.0.6\webapps