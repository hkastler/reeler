# reeler
A Java EE port of Apache Roller

I called the project Reeler as a mashup to reflect both Roller and Java EE.

The code is organized by the Boundary-Control-Entity pattern. I find that it works very well. Thanks to Adam Bien for popularizing it.

The Entity packages are named "entities" because NetBeans(on Windows) did not like the use of the term entity as package name.

There is, as of this writing, still much work to be done, but the blog is operational.

Setup required (for Windows):
* Have Docker for Windows installed
* Have Maven binaries on the PATH
* A writeable directory located at C:\apps\reeler (for use with the docker-compose volumes statement) 
To start:
* Open terminal to reeler\reeler-resources and run .\dockerComposeUp.bat and .\deployToDocker.bat

Docker may ask you to share drive C:\. Check out the reeler-resources\docker-compose.yml to see what's going on. Docker did ask me on one machine, and it automatically created \apps\reeler on deploy.


