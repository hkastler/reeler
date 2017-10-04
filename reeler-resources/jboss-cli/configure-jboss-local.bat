set JBOSS_HOME=\apps\wildfly\wildfly-10.1.0.Final
cp reeler.properties %JBOSS_HOME%\standalone\configuration\.
call %JBOSS_HOME%\bin\add-user.bat admin adminadmin1
start cmd /k %JBOSS_HOME%\bin\standalone.bat -b=0.0.0.0 -bmanagement=0.0.0.0
rem call \apps\wildfly\wildfly-10.1.0.Final\bin\standalone.bat 
sleep 10
call %JBOSS_HOME%\bin\jboss-cli --user=admin --password=adminadmin1  --connect --file=configure-postgresql-local.cli

call %JBOSS_HOME%\bin\jboss-cli --user=admin --password=adminadmin1  --connect --file=configure-security.cli

call %JBOSS_HOME%\bin\jboss-cli --user=admin --password=adminadmin1  --connect --file=configure-mail.cli

call %JBOSS_HOME%\bin\jboss-cli --user=admin --password=adminadmin1 --connect --file=configure-logging.cli

call %JBOSS_HOME%\bin\jboss-cli --user=admin --password=adminadmin1  --connect reload

