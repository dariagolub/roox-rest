## Description

REST application with CRUD

## Installation and Run

```
mvn clean install
```
and then deploy WAR file to servlet container

## API Reference

Initailly project contains 3 users with id 1, 2 and 3.
Authorization header should be presented:

```
Authorization: Bearer {customerId}
```

Examples:
```
# Returns customer info
curl -X GET http://localhost:8080/customerapi/{customerId}
#  Returns list of partner mappings of customer
curl -X GET http://localhost:8080/customerapi/{customerId}/mappings
# Create partner mapping for customer, where {"mapping": "data"} actual mapping object
curl -X POST -d '{"mapping": "data"}' http://localhost:8080/customerapi/{customerId}/mappings
# Update mapping
curl -X PUT -d {mapping} http://localhost:8080/customerapi/{customerId}/mappings/{mappingId}
# Delete partner mapping
curl -X DELETE http://localhost:8080/customerapi/{customerId}/mappings/{mappingId}
```
Mapping json example:

```
{
  "partnerId":12,
  "customerAccountId":130,
  "customerLastName":"Smith",
  "customerFirstName":"Will",
  "customerMiddleName":"Ivanovich",
  "customerAvatarUrl":"https://www.picmonkey.com/_/static/images/index/picmonkey_twitter_02.24fd38f81e59.jpg"
}
```

## Tests

```
mvn test
```
