# ShippingApp-API文档接口-运输微服务

[TOC]

## 1    环境变量

### 默认环境1

| 参数名     | 字段值                   |
| ------- | --------------------- |
| baseUrl | http://localhost:8083 |

## 2    ShippingApp-API文档接口-运输微服务

##### 说明

> 运输微服务接口文档说明。

##### 文档版本

```
1.0
```

## 3    CityController

## 3.1    获取所有城市信息:id和名称

> GET  /city/All

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 获取所有城市信息:id和名称

## 4    CarrierController

## 4.1    承运商结束运输

> POST  /carrier/end-transportation

### 请求体(Request Body)

| 参数名称        | 数据类型   | 默认值 | 不为空   | 描述  |
| ----------- | ------ | --- | ----- | --- |
| beginCityId | int32  |     | false |     |
| beginTime   | string |     | false |     |
| carrierId   | int32  |     | false |     |
| endCityId   | int32  |     | false |     |
| endTime     | string |     | false |     |
| id          | int32  |     | false |     |
| orderIds    | string |     | false |     |
| orderNum    | int32  |     | false |     |
| status      | int32  |     | false |     |
| transportId | int32  |     | false |     |
| type        | int32  |     | false |     |
| weight      | number |     | false |     |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 承运商进行手动到货的处理



## 4.2    获取承运商公司详情

> GET  /carrier/get-all-carrier-name

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

>  获取所有承运商公司的名称

## 4.3    获取承运商信息

> GET  /carrier/get-carrier-info

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

>  获取承运商当前的运力信息

## 4.4    在途运力(已结束)查询

> GET  /carrier/get-transportation-finish-info

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 查询已经到达目的地的运力

## 4.5    在途运力(运输中)查询

> GET  /carrier/get-transportation-in-transit-info

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 查询正在运输中的运力

## 4.6    在途运力(还未发车)查询

> GET  /carrier/get-transportation-waiting-info

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 查询已经装货但还未发车给的运力

## 4.7    承运商开始运输

> POST  /carrier/start-transportation

### 请求体(Request Body)

| 参数名称        | 数据类型   | 默认值 | 不为空   | 描述  |
| ----------- | ------ | --- | ----- | --- |
| beginCityId | int32  |     | false |     |
| beginTime   | string |     | false |     |
| carrierId   | int32  |     | false |     |
| endCityId   | int32  |     | false |     |
| endTime     | string |     | false |     |
| id          | int32  |     | false |     |
| orderIds    | string |     | false |     |
| orderNum    | int32  |     | false |     |
| status      | int32  |     | false |     |
| transportId | int32  |     | false |     |
| type        | int32  |     | false |     |
| weight      | number |     | false |     |

### 响应体

● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述

> 对还没发车的运力，进行手动的发车
