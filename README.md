# angular-7-tutorial-vol-3-Java-REST-MongoDB-backend
Angular 7 Tutorial - Vol. 3 - Java REST MongoDB Backend User Database - Angular, Java, MongoDB

In this (the 3rd of our Angular 7 tutorials we are going to install a Java, MongoDB user database with three users.

We'll examine how to create the user Java application from scratch, import the project into the IntelliJ IDEA tool.

We will be using Apache Maven project builder, IntelliJ IDEA IDE, Robo 3T MongoDB viewer tool among other tools.

The user project will be built using Java 8 and the latest version of Angular 7.

At the end of the tutorial I'll show you how to populate the user MongoDB using a java class UserDB we'll be using to populate 3 users.

We will also examine how to create Swagger UI REST Html documents to test the REST backend if you need to debug the backend without the Angular front-end.

Prerequisites:
MongoDB version 3 and above installed.
Java 8 installed.
Angular 7 and above installed.
Chrome browser
Windows or Unix or Mac command line.
Project structure for two projects as follows:

Angular code (from \angular\ folder)
\dev\javascript\vol-3\user

Java Code (from the root folder)
\dev\java\vol-3\user

Steps:
1. Checkeout the project into a folder using the command: git clone https://github.com/pmkent/angular-7-tutorial-vol-3-Java-REST-MongoDB-backend.git
2. Change to the project root folder and build the project using the command build.bat (on Windows) or ./build.sh
3. Populate the MongoDB database by first building the application using the command bin\build-shade.bat (.sh on unix)
4. Test using http://localhost:8080 on a browser (advisably Chrome). Login using username: userone@gmail.com and password: password
5. To test the backend separately using the url http://localhost:8080/restapi/ui and use the following JSON to test the login get method to get a JWT token:
{
  "username":"userone@gmail.com",
  "password":"password"
}
6. To build (the separate) angular project (under .dev\javascript\vol-3\user) use the command build.bat under the angular project root folder. Make sure the java project is not running (because the angular files are copied into the user java application \webapp\ folder). Rebuild the java project and run it.

Please let me know in the YouTube comments section!

Enjoy!
