## Stocks API

Simple Spring Boot API implementing native auth based on request header 'x-api-key', with an in-mem database and Spring Data.
The API exposes an endpoint to retrieve the latest information of a stock based on its ticker.

API docs can be accessed at https://stocks-api-mrapaport.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config


### Build

To build the project, make sure you've got JDK 11+ and Maven installed:

```shell
mvn clean package
```

### Docker
To build the image, run 
```shell
docker build -t stocks-api:v0.0.1 .
```

## Run

To run the image, use

```shell
docker run -p 8080:8080 -e STOCKS_API_URL=<url> -e STOCKS_API_KEY=<api_key> stocks-api:v0.0.1
```

