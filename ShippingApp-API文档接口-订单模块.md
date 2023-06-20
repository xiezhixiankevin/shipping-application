# ShippingApp-API文档接口-订单微服务

[TOC]

## 1    环境变量

### 默认环境1

| 参数名     | 字段值                   |
| ------- | --------------------- |
| baseUrl | http://localhost:8082 |

## 2    ShippingApp-API文档接口-订单微服务

##### 说明

> 订单接口文档。

##### 文档版本

```
1.0
```

## 3    ShippingOrderController

## 3.1    给订单添加物流信息

> POST  /order/add-logistics-record-by-id

### 请求体(Request Body)

| 参数名称            | 数据类型   | 默认值 | 不为空   | 描述  |
| --------------- | ------ | --- | ----- | --- |
| content         | string |     | false |     |
| createTimestamp | string |     | false |     |
| id              | int32  |     | false |     |
| orderId         | int32  |     | false |     |
| state           | int32  |     | false |     |
| updateTimestamp | string |     | false |     |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

## 3.2    创建订单

> POST  /order/create

### 请求体(Request Body)

| 参数名称                | 数据类型    | 默认值 | 不为空   | 描述  |
| ------------------- | ------- | --- | ----- | --- |
| cargoInfo           | string  |     | false |     |
| cargoName           | string  |     | false |     |
| cargoWeight         | number  |     | false |     |
| consumerId          | int32   |     | false |     |
| createTimestamp     | string  |     | false |     |
| estimateCapacity    | number  |     | false |     |
| estimateDistance    | number  |     | false |     |
| id                  | int32   |     | false |     |
| inTransitId         | int32   |     | false |     |
| latestDeliveryTime  | string  |     | false |     |
| orderId             | string  |     | false |     |
| orderProviderCharge | number  |     | false |     |
| orderRealCharge     | number  |     | false |     |
| orderSenderCharge   | number  |     | false |     |
| providerId          | int32   |     | false |     |
| receiverAddress     | string  |     | false |     |
| receiverName        | string  |     | false |     |
| receiverPhoneNumber | string  |     | false |     |
| refrigerated        | boolean |     | false |     |
| senderAddress       | string  |     | false |     |
| senderName          | string  |     | false |     |
| senderPhoneNumber   | string  |     | false |     |
| state               | int32   |     | false |     |
| status              | int32   |     | false |     |
| updateTimestamp     | string  |     | false |     |
| urgentLevel         | int32   |     | false |     |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

## 3.3    列出某个客户的订单

> GET  /order/get-consumer-orders

### 请求参数(Query Param)

| 参数名称        | 默认值 | 描述          |
| ----------- | --- | ----------- |
| ifCompleted |     | ifCompleted |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

## 3.4    根据订单id返回订单信息

> GET  /order/get-order-by-order-id

### 请求参数(Query Param)

| 参数名称    | 默认值 | 描述      |
| ------- | --- | ------- |
| orderId |     | orderId |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||



## 3.5    获取订单的所有物流信息，默认时间排序

> GET  /order/list-logistics-record

### 请求参数(Query Param)

| 参数名称    | 默认值 | 描述      |
| ------- | --- | ------- |
| orderId |     | orderId |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||



## 3.6    修改某个订单的信息

> PUT  /order/update-order-by-id

### 请求体(Request Body)

| 参数名称                | 数据类型    | 默认值 | 不为空   | 描述  |
| ------------------- | ------- | --- | ----- | --- |
| cargoInfo           | string  |     | false |     |
| cargoName           | string  |     | false |     |
| cargoWeight         | number  |     | false |     |
| consumerId          | int32   |     | false |     |
| createTimestamp     | string  |     | false |     |
| estimateCapacity    | number  |     | false |     |
| estimateDistance    | number  |     | false |     |
| id                  | int32   |     | false |     |
| inTransitId         | int32   |     | false |     |
| latestDeliveryTime  | string  |     | false |     |
| orderId             | string  |     | false |     |
| orderProviderCharge | number  |     | false |     |
| orderRealCharge     | number  |     | false |     |
| orderSenderCharge   | number  |     | false |     |
| providerId          | int32   |     | false |     |
| receiverAddress     | string  |     | false |     |
| receiverName        | string  |     | false |     |
| receiverPhoneNumber | string  |     | false |     |
| refrigerated        | boolean |     | false |     |
| senderAddress       | string  |     | false |     |
| senderName          | string  |     | false |     |
| senderPhoneNumber   | string  |     | false |     |
| state               | int32   |     | false |     |
| status              | int32   |     | false |     |
| updateTimestamp     | string  |     | false |     |
| urgentLevel         | int32   |     | false |     |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||



## 3.7    更新订单表涉及订单状态

> POST  /order/update-orderState-by-transitId

### 请求参数(Query Param)

| 参数名称        | 默认值 | 描述          |
| ----------- | --- | ----------- |
| inTransitId |     | inTransitId |
| state       |     | state       |

### 响应体

● 200 响应数据格式：JSON

##### 接口描述

> 
