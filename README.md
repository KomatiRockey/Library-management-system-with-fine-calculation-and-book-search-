# Library-management-system-with-fine-calculation-and-book-search-
# Library Management System (Spring Boot)

## Requirements
- Java 17 or newer
- Maven

## Run locally
1. Build:
   mvn clean package

2. Run:
   java -jar target/*.jar

3. Open: http://localhost:8080

## Notes
- Uses H2 in-memory DB by default (no external DB needed).
- To use MySQL, set environment variables:
  - JDBC_DATABASE_URL (e.g. jdbc:mysql://host:3306/dbname)
  - DB_USERNAME
  - DB_PASSWORD
  - then rebuild and run.

## Deploy to Render
1. Add `render.yaml` to repo root (already included).
2. Push repo to GitHub.
3. Create Web Service on Render -> choose repo -> Render will read render.yaml and build.
4. If Render build fails with `mvn: command not found`, update buildCommand to install maven first (e.g., apt-get install maven) or use a Dockerfile.

## Deploy to Heroku
1. Create Procfile:
   web: java -jar target/*.jar
2. Set up ClearDB MySQL or use JawsDB add-on for MySQL (optional).
3. Push to Heroku and set environment variables as needed.
