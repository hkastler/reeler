@echo "build reeler"
call cd ..\reeler
call mvn clean package -DskipTests=true -T 1.5C
@echo "mvn package successful"
call cp .\target\reeler.war \apps\reeler\.
echo "copy to \apps\reeler\ successful"
call cd ..\reeler-resources
rem call .\dockerComposeUp.bat