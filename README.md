# jdbc-tester

A simple command line Spring boot app to test JDBC connection.

# How to run

Clone this repository and then

```
mvn clean package
```

Then to run the application below replacing the the parameters denoted with `<>` with actual values.

```
java -jar target/jdbc-tester-1.0.jar <schema_name> <schema_password> jdbc:oracle:thin:@//<host>:<port>/<SID>
```

