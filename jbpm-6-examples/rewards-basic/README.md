rewards-basic
=============

This is an example web application for jBPM 6. It was created by forking the rewards-basic application by Toshiya Kobayashi:
https://github.com/tkobayas/jbpm5example/tree/master/rewards-basic

Also the project structure has changed from Java EE 5 to Java EE 6.

This simple example aims to provide an example usage of:
- Human tasks
- Persistence
- Transactions
- Singleton session manager
- Context dependency injection
- Infinispan
- Maven

This application uses single session. Other session strategies (session managers) can be demonstrated later. So far the example supports only dependencies from jBPM 6 as community project.


### Steps to run
- Make sure you have at least Java 6 and Maven 3 installed
- Download somewhere JBoss EAP 6.1 (was tested on JBoss EAP 6.1, other versions should work too)
- Start the application server (default datasource is ExampleDS, the same as in EAP, so the example works out of the box):
 - cd jboss-eap-6.1/bin
 - ./standalone.sh
- Build and deploy the example application:
 - cd jbpm-6-examples/rewards-basic
 - mvn clean package
 - mvn jboss-as:deploy
- Visit http://localhost:8080/rewards-basic/ with a web browser
 - [Start Reward Process] is to start a new process
 - [John's Task] is to list John's tasks and approve them
 - [Mary's Task] is to list Mary's tasks and approve them


