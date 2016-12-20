# microservices-demo



You can run the system in your IDE by running the all servers in order: _RegistrationService_, _AccountsService_ , _eBusinessService_ , _OrderService_ and _WebService_.

Open the Eureka dashboard [http://localhost:1111](http://localhost:1111) in your browser to see that the `ACCOUNTS-SERVICE`, `WEB-SERVICE` and other applications that have registered.  Next open the Demo Home Page of Web service[http://localhost:3333](http://localhost:3333) in and click one of the demo links.

To run the projects, open your Terminal(MacOS, Linux) and do the following:

 1. In each window, change to the directory where you cloned the project
 2. In the first window, build the application using `mvn clean install -DskipTests`
 3. In the same window run: `java -jar target/microservice-BillingSystem-demo-1.1.0.RELEASE.jar reg` and wait for it to start up
 4. Switch to the second window and run: `java -jar target/microservice-BillingSystem-demo-1.1.0.RELEASE.jar accounts` and again wait for
 it to start up
 5. In the third window run: `java -jar target/microservice-BillingSystem-demo-1.1.0.RELEASE.jar web`
 6. In a new window, run up a eBusinessAccount-server using HTTP port 2223: `java -jar target/microservice-BillingSystem-demo-1.1.0.RELEASE.jar ebus`
 7. In a new window, run up a eBusinessAccount-server using HTTP port 2223: `java -jar target/microservice-BillingSystem-demo-1.1.0.RELEASE.jar order`
 
 8. In your favorite browser open the same two links: [http://localhost:1111](http://localhost:1111) and [http://localhost:3333](http://localhost:3333)

You should see servers being registered in the log output of the first (registration) window.


