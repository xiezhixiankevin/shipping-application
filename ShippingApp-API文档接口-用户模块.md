# ShippingApp-API文档接口测试
[toc]
## 1	环境变量

### 默认环境1
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:8088|


## 2	UserAccountController

## 2.1	用于获取注册验证码

> GET  /user/get-register-code
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|email||email|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述
> 改接口用于向邮箱发送注册验证码，内部封装了邮箱发送服务




## 2.2	用于用户登录

> POST  /user/login
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|email||email|
|password||password|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述
> 根据邮箱和密码进行登录




## 2.3	用于用户注册

> POST  /user/register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| carrierId|int32||false||
| code|string||false||
| email|string||false||
| id|int32||false||
| identity|int32||false||
| password|string||false||
| token|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||
| success|boolean||false||

##### 接口描述
> 只有邮箱验证码正确，才可以进行注册！



