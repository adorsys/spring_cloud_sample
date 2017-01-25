# Spring Cloud Sample

Example application using Spring Cloud Config.
The configuration can be found at [spring_cloud_config_sample](https://github.com/adorsys/spring_cloud_config_sample).

## Getting Started

```
# build all projects
BASE_DIR=`pwd` && for dir in */; do cd ${BASE_DIR}/${dir} && mvn package; done && cd ${BASE_DIR}
# dockerize and start
docker-compose build && docker-compose up
```

The Product and Cart Service are configured via a config server and directly available under following URIs:

```
curl -i localhost:8082/product/
curl -i localhost:8081/cart/4711/count/
```

A zuul gateway is running on port 8080 will forward requests to `/product` and `/cart`.
It will also debug all requests.
If the rate of requests to `/product` exceeds 50 requests/seconds it will forward to a static fallback.

```
ab -n 100 localhost:8080/product/
```