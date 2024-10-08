# Kitchen Sink V2

## Setup Instructions

### Getting the codebase
* Clone the code repository using the following command:
```git clone https://github.com/bogdandx/kitchensink-v2.git```
### Configure the legacy database to mixed mode
Database
* For the purpose of delivery the application incrementally, the modernized application connects to the same database as the legacy application. This is convenient as it allows for changes made using one application to be visible in the other application. This was achieved by configuring the legacy application to run H2 in mixed mode, by making the following change:
  In the kitchensink-quickstart-ds.xml file in the legacy application file replace:

```<connection-url>jdbc:h2:mem:kitchensink-quickstart;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1</connection-url>```

with:

```<connection-url>jdbc:h2:/data/kitchensinkDB;AUTO_SERVER=TRUE;AUTO_SERVER_PORT=9090</connection-url>```

### Running the application
* Open KichenSinkApplication in IntelliJ.
* Select Run KichenSinkApplication.
* The application is by default available on localhost at port 8082. This can be changed from the application.properties file.

### Running the tests
* ```mvn test -P modern``` runs all the tests (unit + Cucumber) against the modernized Rest Api. The application must have been started prior to executing this command.
* ```mvn test -P legacy``` runs all the tests (unit + Cucumber) against the legacy Rest Api. The legacy application must have been started prior to executing this command.
* The urls needed by the Cucumber tests for both the legacy and modern applications are defined in the pom.xml file.
* Note that because neither application is deployed to an environment, the CI Cucumber and repository tests are turned off. They only work locally with the 2 applications running.