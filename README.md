# la_test
Source code for the Love Admin test project

# notes
If the gson and/or mssql-jdbc jars are missing from the project they can be added from the "jars" folder. The correct jdbc jars to use depends on the version of java installed. The ones contained in the project are for java version 11, but these can be replaced with different ones from the "jars" folder.

I implemented the test application using Spring Boot in Eclipse. It should be possible to open the work space in Eclipse and load the project from there which is called "DataDisplayExample".  The service I implemented returns the data as a stream and this is fetched and inserted into an html table by javascript implemented on the page (index.jsp). I did this to try to avoid loading the entire dataset into memory on the server. I couldn't test the application with the provided log-in details for the test database but did so with my own login and seems to work. I changed the database connection details to mach those specified in the instructions.  The java files including the controller class containing the service method are in /src/main/java/com/loveadmintest/. The controller class is called "AppController" and the service method is called "getData()". The jsp page is called "index" and is located in /src/main/webapp/WEB-INF/view/. The class "App" is the entry point for the application. 
