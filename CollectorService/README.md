# Collector 

The Collector service is collecting data from local MySQL database zipkin and translating the data into JSON object.

In the directory where you clone the project, do the following:

 1. In the terminal, build the application using `mvn clean install -DskipTests`
 2. In the same window run: `java -jar target/Collector-Service-1.1.0.RELEASE.jar col` and wait for it to start up
 3. In your favorite browser open the links: [http://localhost:2225](http://localhost:2225) and you will see the UI of the Collector

