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

Request format:
```
curl -X POST \
  http://localhost:8080/generate-plan \
  -H 'Content-Type: application/json' \
  -d '{
"loanAmount": "5000",
"nominalRate": "500.0",
"duration": 24,
"startDate": "2018-01-01T00:00:01Z"
}'
```

Improvement Scope:

1. Give result in pagination.
2. Api Documentation
