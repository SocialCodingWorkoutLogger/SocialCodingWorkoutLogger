# SocialCodingWorkoutLogger

### Running the project

Before attempting to run locally, ensure you have MySQL installed and a have a database up and running.

Create the following environment variables in accordance with your MySQL database configuration.
```
DB_URL=jdbc:mysql://localhost:3306/{Databases's Name}
DB_USER=
DB_PASS=
```

To run locally:
```
./mvnw spring-boot:run
```