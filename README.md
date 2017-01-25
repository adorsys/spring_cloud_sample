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

### Testing

```
curl -i localhost:8082/product/
curl -i localhost:8081/cart/4711/count/
```