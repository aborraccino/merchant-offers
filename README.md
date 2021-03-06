# Merchant Offers Web
A simple REST API service allows a merchant to create a new offer with one or more details.

## Requirements
The RESTful software service will allow a merchant to create
a new simple offer. Offers, once created, may be queried. 
After the period of time defined on the offer it should expire and further requests 
to query the offer should reflect that somehow. 
Before an offer has expired users may cancel it.

## Toolset
- Spring Boot (embedded Tomcat)
- Spring MVC
- Spring Data JPA
- Hibernate
- H2 DB (in-memory database)
- Maven
- Git
- Junit 5
- Mockito
- springfox-swagger2
- Docker

## Prerequisites without the usage of Docker
- Java 14
- GIT
- Maven

## MVC Models
The application design follow the MVC design pattern.
It consists of two controllers:

- OfferController
- OfferDetailController

JSON model:
- OfferDto (currency, description, expirationDelay, offerCode, offersDetail, price)
- OfferResponseDto (currency, description, expired, offerCode, offerExpireDate,	offerId, offerStartDate, offersDetail, price)
- OffersDetailDto (offerDetailCode,	offerDetailDescription,	quantity)
- OfferDetailResponseDto (offerDetailCode, offerDetailDescription, offerDetailId, quantity)

DB Models:
- OFFER (OFFER_ID, CURRENCY, OFFER_DESCRIPTION, OFFER_CODE, OFFER_EXPIRE_DATE, OFFER_START_DATE, PRICE)  
- OFFER_DETAIL(OFFER_DETAIL_ID, OFFER_DETAIL_CODE, OFFER_DESCRIPTION, QUANTITY, OFFER_ID)

## Implemented requirements
### Endpoints
- endpoint for creating Offers with zero or more Details (one to many relationship)
- endpoints for retrieve and update Offer
- endpoint for manually expire an Offer (logical delete)
- endpoints for creating, updating, retrieving Detail associated to an existing offer

### Offer Expiration
If during the offer creation, is specified the field `expirationDelay` (expressed in second for simplicity), the OFFER_EXPIRE_DATE will be 
automatically calculated and persisted to the Offer model. If the current date is greater than OFFER_EXPIRE_DATE, 
the offer will expire and no action can be performed.
Moreover, if the field `expirationDelay` is empty or null, the Offer will never expire, unless the user calls the API for expire it explicitly.

### Automatic Id generation
The primary keys have been automatically generated by Hibernate, using a random UUID type, to ensure uniqueness
and avoid to hard coding into json.

### Design Pattern
Used Adapter Pattern in order to add new functionalities to OfferDetailImpl:
- The OfferDetailServiceAdapterImpl uses composition over inheritance in order to extends OfferDetailService and OfferService. 
During Offer detail creation/update, if the offer associated does not exist the method throws ResourceNotFoundException.

### Testing
- JPA integration test with DataJpaTest that use an embedded in-memory database by default
- Service integration test with Mockito
- Spring MVC integration test with Spring WebMvcTest and MockMvc Object
- Used a Lambda expression in order to pass a function that returns a fixed date (`Supplier<LocalDateTime> offerClock = () -> dummyLocalDateTime`), used to calculate the expiration date.
During the software execution the Lambda returns the current date of the system (`Supplier<LocalDateTime> offerClock = () -> LocalDateTime.now()`)

## Quick start
Use the following command:
- `git clone https://github.com/aborraccino/merchant-offers.git`
- `cd merchant-offers/`
### With Docker
- `docker build -t merchant-offers .`
- `docker run -p 8080:8080 --name merchant-offers --rm merchant-offers`
### Without Docker
- `mvn clean install`
- `java -jar target/merchant-offers-0.0.1-SNAPSHOT.jar`

### Application endpoints
- the embedded Tomcat starts at `http://localhost:8080/merchant-offers`
- swagger GUI is available on `http://localhost:8080/merchant-offers/swagger-ui.html`
- H2 DB console is available on `http://localhost:8080/merchant-offers/h2-console`. 
Access with user `sa` (no password) and put `jdbc:h2:mem:offers` as JDBC URL

## Usage Examples
All REST API can be used with Swagger GUI at the URL shown above.

Some examples using CURL command are described below.

### Create an Offer (POST /merchant/store/v1/api/offers)

JSON example with two items:
```javascript
{
  "currency": "EUR",
  "description": "A special Offer!",
  "expirationDelay": 180,
  "offerCode": "offer1",
  "offersDetail": [
    {
      "offerDetailCode": "offerDetail1",
      "offerDetailDescription": "detail 1",
      "quantity": 1
    },
    {
      "offerDetailCode": "offerDetail2",
      "offerDetailDescription": "detail 2",
      "quantity": 2
    }
  ],
  "price": 10
}
```
Response
```javascript
{
  "offerId": "3263657c-edb7-40e4-b6c2-d55e3f768862",
  "offerCode": "offer1",
  "description": "A special Offer!",
  "price": 10,
  "currency": "EUR",
  "offersDetail": [
    {
      "offerDetailCode": "offerDetail1",
      "offerDetailDescription": "detail 1",
      "quantity": 1
    },
    {
      "offerDetailCode": "offerDetail2",
      "offerDetailDescription": "detail 2",
      "quantity": 2
    }
  ],
  "offerStartDate": "2020-07-28 00:04:48",
  "offerExpireDate": "2020-07-28 00:07:48",
  "expired": false
}
```

Notes:
- mandatory fields are: "currency", "offerCode", and "price"
- the "expirationDelay" is expressed in seconds
- the accepted currencies are EUR, USD and GBP

Command:
````
curl -X POST "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"currency\": \"EUR\", \"description\": \"A special Offer!\", \"expirationDelay\": 180, \"offerCode\": \"offer1\", \"offersDetail\": [ { \"offerDetailCode\": \"offerDetail1\", \"offerDetailDescription\": \"detail 1\", \"quantity\": 1 }, { \"offerDetailCode\": \"offerDetail2\", \"offerDetailDescription\": \"detail 2\", \"quantity\": 2 } ], \"price\": 10}"
````

### Expire an Offer (DELETE /merchant/store/v1/api/offers/{offerId})
Command for expire an offer manually:
````
curl -X DELETE "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/a40db983-f1d9-45d2-bbe7-c4655f956bb5" -H "accept: */*"
````

### Retrieve an Offer expired (GET /merchant/store/v1/api/offers/{offerId})
Command:
````
curl -X GET "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/a40db983-f1d9-45d2-bbe7-c4655f956bb5" -H "accept: */*"
````
- HTTP Response: 404
- Response body:
````
{
  "code": "04",
  "message": "Offer expired"
}
````
### Retrieve an Offer not expired (GET /merchant/store/v1/api/offers/{offerId})
Command:
````
curl -X GET "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/8d7efde0-a7de-46a0-88f3-436708d7726c" -H "accept: */*"
````
- HTTP Response: 200
- Response body:
````
{
  "offerId": "8d7efde0-a7de-46a0-88f3-436708d7726c",
  "offerCode": "offer6",
  "description": "A special Offer!",
  "price": 10,
  "currency": "EUR",
  "offersDetail": [
    {
      "offerDetailCode": "offerDetail7",
      "offerDetailDescription": "detail 1",
      "quantity": 1
    },
    {
      "offerDetailCode": "offerDetail8",
      "offerDetailDescription": "detail 2",
      "quantity": 2
    }
  ],
  "offerStartDate": "2020-07-28 00:16:25",
  "offerExpireDate": null,
  "expired": false
}
````

### Add a new Detail to an existing Offer (POST /merchant/store/v1/api/offers/{offerId}/details)
Request body:
````
{
  "offerDetailCode": "12",
  "offerDetailDescription": "new detail!",
  "quantity": 33
}
````

Command:
````
curl -X POST "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/8d7efde0-a7de-46a0-88f3-436708d7726c/details" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"offerDetailCode\": \"12\", \"offerDetailDescription\": \"new detail!\", \"quantity\": 33}"
````
Body Response:
````
"2f56baae-5b45-4cff-83e6-a63199f06d68"
````

### Add a new Detail to an not existing Offer (POST /merchant/store/v1/api/offers/{offerId}/details)
Request body:
````
{
  "offerDetailCode": "12",
  "offerDetailDescription": "new detail!",
  "quantity": 33
}
````

Command:
````
curl -X POST "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/8d7efde0-a7de-46a0-88f3-436708d7726d/details" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"offerDetailCode\": \"12\", \"offerDetailDescription\": \"new detail!\", \"quantity\": 33}"
````
- HTTP response code: 404
- Body Response:
````
{
  "code": "01",
  "message": "Offer not found"
}
````

### Retrieve an Detail associated with an Offer (GET /merchant/store/v1/api/offers/{offerId}/details/{offerDetailId})
Command:
````
curl -X GET "http://localhost:8080/merchant-offers/merchant/store/v1/api/offers/0db98fa1-06f8-4205-a8d3-7bcdc8e9f39f/details/afb8b918-3c5e-49a8-893a-16c4d7c1f6a5" -H "accept: */*"
````

Response Body:
````
{
  "offerDetailId": "afb8b918-3c5e-49a8-893a-16c4d7c1f6a5",
  "offerDetailCode": "123",
  "offerDetailDescription": "new detail!",
  "quantity": 333
}
````