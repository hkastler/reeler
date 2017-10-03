@echo "build reeler"
call mvn -e clean package -DskipTests=true
@echo "mvn package successful"
call cp .\target\reeler.war \apps\reeler\.
echo "copy to \apps\reeler\ successful"