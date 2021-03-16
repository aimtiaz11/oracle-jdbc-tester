# oracle-jdbc-tester

[![Maven Build](https://github.com/aimtiaz11/oracle-jdbc-tester/actions/workflows/maven.yml/badge.svg)](https://github.com/aimtiaz11/oracle-jdbc-tester/actions/workflows/maven.yml)

A simple command line application to test JDBC connection to Oracle Database.

## How to run

Clone this repository and then run:

```
mvn clean package
```
Or download the JAR file from [release](https://github.com/aimtiaz11/jdbc-tester/releases) page. 

Execute the JAR file with the following 3 parameters with schema name, password and JDBC connection details:

```sh
java -jar target/jdbc-tester-1.0.jar <schema_name> <schema_password> jdbc:oracle:thin:@//<host>:<port>/<SID>
```

### Secure your credentials

When running this tool ad-hoc, a good security practice would be read the DB username and password into a variable by using `read` command in Linux (or similar) and then execute the JAR file.

This prevents DB credentials being stored in `~/.bash_history`.


```sh
java -jar target/jdbc-tester-1.1.jar $DB_USER $DB_PASS jdbc:oracle:thin:@//<host>:<port>/<SID>
```

## How it works

The application connects to the Oracle database and executes a single SQL query: `select sysdate from dual` and prints the output. 

If it cannot connect for whatever reason, it will fail by logging an error message.

There is a hardcoded connection timeout set to 10 seconds.

## License

(The MIT License)

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
