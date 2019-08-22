# Annuity plan 

A Restful API that provides Loan premium details of life time

How to run application:

Docker:
```
./mvnw clean package
sudo docker build -t annuity .
sudo docker run -p 8080:8080 annuity:latest
```
Maven:
```
./mvnw clean package
./mvnw spring-boot:run  
```
Improvement Scope:
Give result in pagination.
