# reeler
A Java EE port of Apache Roller

I called the project Reeler as a mashup to reflect both Roller and Java EE.

The code is organized by the Boundary-Control-Entity pattern. I find that it works very well. Thanks to Adam Bien for popularizing it.

I called the Entity packages "entities" because NetBeans(on Windows) did not like the use of the term entity as package name.

There is as of this writing, still much work to be done, but the blog is operational.

To start:
-Have Docker for Windows(or Mac) installed
-Have Maven binaries on the PATH
-open terminal to reeler\reeler and run .\dockerComposeUp.bat and .\deployToDocker.bat 


