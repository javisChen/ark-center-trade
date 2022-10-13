# 介绍
ark-center-order

# 运行

## Docker Build
```shell
docker build -f ./ark-center-order-start/Dockerfile -t ark-center-order:v1 ./ark-center-order-start
```
## Docker Run
```shell
docker run -p 8083:8083 --name ark-center-order -d \
-e NACOS_DISCOVERY_IP=172.24.80.20 \
-e NACOS_DISCOVERY_SERVER_ADDR=172.24.80.20:8848 \
-e NACOS_CONFIG_SERVER_ADDR=172.24.80.20:8848 \
-e SYS_OPT=-DSpring.profiles.active=dev \
ark-center-order:v1
```

# 软件架构

## 组件说明

| 组件名称                       | 描述           | 版本    |
|----------------------------|--------------|-------|
| ark-center-order-acl     | 防腐层          | 1.0.0 |
| ark-center-order-api     | 开放接口层        | 1.0.0 |
| ark-center-order-dao     | 数据访问层        | 1.0.0 |
| ark-center-order-manager | 通用业务处理层      | 1.0.0 |
| ark-center-order-service | 具体业务以及内部访问转发 | 1.0.0 |
| ark-center-order-start   | 启动层          | 1.0.0 |


## 使用说明
```
mvn install到本地（如果不想运行测试，可以加上-DskipTests参数）
```