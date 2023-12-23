# Cash Settlement

Subjective Task of KredX

---
Note:- 
All the configuration in application.properties are to run this cash settlement project on local, you can change it directly OR these vales can be Override by setting below Environment variables.

---
## Db Connection
| Environment Variable Name   | Example Value                                  |
|-----------------------------|------------------------------------------------|
| SPRING_DATASOURCE_URL       | jdbc:mysql://${MYSQL_HOST:localhost}:3306/demo |
| SPRING_DATASOURCE_USERNAME  | root                                           |
| SPRING_DATASOURCE_PASSWORD  | XXXXXXX                                        |


## Db Connection
| Environment Variable Name | Example Value           |
|---------------------------|-------------------------|
| JWT_SECRET                | XXXXX (Base 64 Encoded) |
| JWT_TOKEN_VALIDITY        | 30 (In Minutes)         |

---

## How To Run Project ?
1. Create empty db schema and update its name in application.properties/environment
2. Update db connection username and password in application.properties/environment
3. default port is 8081, You can update it in application.properties/environment
4. Run Spring Boot Project <BR>
Command: `./mvnw spring-boot:run` <BR>
Or <BR>
You can run it directly from your preferred IDE. (DemoApplication.java)
5. Check Postman api document and do play with APIs <BR>
   Postman Document : [Click Here](https://documenter.getpostman.com/view/10385340/2s9YkrcLQf).

------

## Code Base Structure.
Base Package : com.kredx.cashsettlement

1. application.properties (In the resources)
2. Configurations package -> config
3. User Module package (Registration, Login and Logout) -> user
4. Transaction Module package (Lend, Borrow, Transactions and Report) -> transaction

-----

## Api code structure.
1. API -> Controller -> Service -> ServiceImpl -> JPARepository -> return response
2. Also used JPA Entity and DTO as per need.

-----
