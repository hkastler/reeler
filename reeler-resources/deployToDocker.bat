@echo "build reeler"
call cd ..\reeler
call mvn -f pom.xml -e clean package -DskipTests=true
@echo "mvn package successful"
call cp .\target\reeler.war \apps\reeler\.
echo "copy to \apps\reeler\ successful"
call cd ..\reeler-resources
rem call .\dockerComposeUp.bat