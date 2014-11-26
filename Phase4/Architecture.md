# Phase 4 - Architecture

This system uses a classic client-server architecture. There are two codebases, one for the client, and one for the server.

Communication between the client and server is done using the HTTP protocol. HTTPS with encryption is optionally supported. All data is sent as text strings and serialized using the JSON format. Upon receipt at either end, the data is parsed using an open source JSON library that we included.

The server uses the Java Servlet API for handling HTTP. The Jetty servlet container is being used in an embedded configuration; this allows for miminal effort for configuring the server to run on any desired system. We are able to build a single JAR file containing all of the resources required to run the server, and it will be able to run on any system which has a compatible JRE, without needing to install any other dependencies. The server does not maintain any state between requests, all state is stored in the database. There are two separate categories that the server code falls in to; request handling (Servlets) and data access logic. The servlets directly process HTTP requests and are invoked by the servlet container. Each servlet will call on data access functions in order to process and respond to the request. In turn, the data access functions will enforce business logic and communicate with the persistent storage (MySQL).

The database used by the server is an external MySQL instance, for which the connection parameters are configurable. Each request to the server will open potentially one or multiple connections to the database in order to complete the desired operation. Login sessions are tracked in the database, as well as all other data. The client does not store any data locally, except for connection prefrences and any in-memory data required to render the screens. We evaluated using an embedded database engine such as Derby or SQLite, but chose a full external RDBMS server since we needed better support for concurrent access to the DB. In addition, it allows for a more flexible architecture where the DB service is not required to run on the same system as the application server. Where possible, constraints in the DB schema are used to enforce business logic, which provides protection and guidance for writing the procedural code that accesses the database.

The client uses only the Android libraries in order to run the user interface. In the background, it loads data via HTTP and parses it using the same JSON library we used for the server. This data is used to populate the fields on the user interface.

## Architecture Diagram

![Architecture Diagram](https://github.com/csc301-fall2014/Proj-Evening-Team6-repo/blob/master/Phase4/architecture_diagram.png)
