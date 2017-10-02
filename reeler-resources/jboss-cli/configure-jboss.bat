@echo off
cls
call \apps\wildfly\wildfly-10.1.0.Final\bin\jboss-cli --user=admin --password=adminadmin1 --connect --file=configure-logging.cli
pause
call \apps\wildfly\wildfly-10.1.0.Final\bin\jboss-cli --user=admin --password=adminadmin1  --connect --file=configure-postgresql.cli
pause
call \apps\wildfly\wildfly-10.1.0.Final\bin\jboss-cli --user=admin --password=adminadmin1  --connect --file=configure-mail.cli

