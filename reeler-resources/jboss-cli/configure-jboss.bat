@echo off
cls
call \apps\wildfly\wildfly-10.1.0.Final\bin\jboss-cli --connect --file=configure-logging.cli
pause
call \apps\wildfly\wildfly-10.1.0.Final\bin\jboss-cli --connect --file=configure-postgresql.cli
pause

