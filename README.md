# Monthly Statement Processor for Customer Records
 
Statement Processor Application receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated based on below conditions

  1.All transaction references should be unique
  
  2.End balance needs to be validated
  
  ### Output: 
  Application should return the transaction reference and description of each of the failed records.
  
  ### Input:
  
  Input file contains below fields:
  
- 1.Transaction reference	- A numeric value
- 2.Account number	- An IBAN
- 3.Account	- IBAN
- 4.Start Balance	- The starting balance in Euros
- 5.Mutation	- Either and addition (+) or a deducation (-)
- 6.Description	- Free text
- 7.End Balance	The end balance in Euros

### Installing & Running

#### Clone this repo into your local:
```
git@github.com:minaldevikar/statementprocessor.git
```

####  Build using maven
```
mvn clean install
```

#### Start the app
```
mvn spring-boot:run
```

### Steps to run the application:
- Clone the project using git url - https://github.com/minaldevikar/statementprocessor.git
- Run maven command to install dependency.
- Compile and run the application
- This Web service application have one active url to process Csv/Xml files -  http://localhost:8085/processor/api/v1/upload
- Upload input csv/xml file in the service using postman client.
- The input file will be validated based on two conduction mentioned in the problem statment.(validation condition mentioned in expected output section)
    1. Duplicate Reference check 
    2. End balance calculation check. (endbalance = startbalance – mutation)
- Finally invalid records will be getting as webservice response with status code.

## Database
This application is using H2 in-memory database, which (database as well as data) will be removed from memory when the application goes down.
While the application is running, you can access the [H2 Console](http://localhost:8085/console) if you want to see the data outside the application.
You can connect to the DB using the JDBC URL: 'jdbc:h2:mem:processor' and user 'minal' with empty password. 

All records getting saved in CUSTOMER_RECORD table for audit purpose.


## Code quality
SonarQube scan: `mvn clean verify -P sonar`

SonarQube dashboard 
https://sonarcloud.io/project/overview?id=minaldevikar_statementprocessor
