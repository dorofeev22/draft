### draft
model of the web-application
### Install
install Java 8
install postgresql 10+
run /back/scripts/init-db.sql (change user and password if you need)
run mvnw clean compile
run mvnw package
create your own application.yml like /back/src/main/resources/application.yml with correct datasource info
take \back\target\draft-back-0.0.1-SNAPSHOT.jar
run java -jar draft-back-0.0.1-SNAPSHOT.jar --spring.config.name=application.yml 
