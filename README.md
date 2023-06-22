# **作业文档**

**组员：**

黄泽霖 20231010

韩楚博 20291140

谢志贤 20251079

**四个作业的交付物在本仓库的 4 个分支，分别为 Assignment1、Assignment2、Assignment3和Assignment4**


tips：本次作业，通过代码截取、流程图、时序图等方式展示出来。首先介绍总体的项目，然后根据作业的要求依次讲述。图片使用了图床显示，如果显示不出来请尝试刷新。谢谢老师！

## **项目背景及概览**

​      本项目实现了一个货运管理系统。系统涵盖了**承运商**和**客户**两个主要角色，并为他们提供了一系列功能。

​      对于客户而言，他们可以通过系统新建订单、查询订单状态和查看个人订单。在新建订单过程中，客户可以根据自己的需求选择不同的承运商，并可根据特定要求进行个性化选项的配置。这样，客户可以根据运输需求、时间要求和预算等因素，灵活选择最适合自己的承运商和服务。

​      而对于承运商来说，该系统提供了订单管理功能，使其能够高效地处理和管理所有属于本承运商的订单。承运商可以通过系统进行发货和到货操作，更新订单状态。此外，本系统还允许承运商管理自己的运输工具，如飞机、大中小型卡车等。

## **项目技术栈**


- 基础框架：Spring Boot、Spring MVC、MyBatis-Plus
- 升级框架：Spring Cloud、Spring Security
- 基本组件：Eureka、Gateway、Feign、MySQL、Redis、Kafka、Hystrix、Lombok、Fastjson、Swagger、Rabbitmq
- 前端：在不同作业中，Thymeleaf, Vue.js, ElementUI
- 服务器：阿里云服务器、腾讯云服务器


**项目管理：**

- GitHub
- Git
- 飞书 Lark

## 系统架构图

![img](https://p.ipic.vip/3yill0.png)

## 系统功能图

![img](https://p.ipic.vip/jov5ln.png)

# 接口展示

所有的接口文档都放在了 master 分支下，由 Swagger 导出（重构 Rest API 之后的）

![img](https://p.ipic.vip/1kjnmx.png)
[shipping-application/ShippingApp-API文档接口-承运商模块.md at master · xiezhixiankevin/shipping-application (github.com)](https://github.com/xiezhixiankevin/shipping-application/blob/master/ShippingApp-API文档接口-承运商模块.md)

[shipping-application/ShippingApp-API文档接口-用户模块.md at master · xiezhixiankevin/shipping-application (github.com)](https://github.com/xiezhixiankevin/shipping-application/blob/master/ShippingApp-API文档接口-用户模块.md)

[shipping-application/ShippingApp-API文档接口-订单模块.md at master · xiezhixiankevin/shipping-application (github.com)](https://github.com/xiezhixiankevin/shipping-application/blob/master/ShippingApp-API文档接口-订单模块.md)




## **Assignment1 - 使用****Spring MVC****进行运输****Web应用****开发**

### **创建和配置****Spring MVC****项目、整合Spring Data JPA/Mybatis-Plus**

数据库表详情：

![img](https://p.ipic.vip/tnmeiz.png)

![img](https://p.ipic.vip/zusb45.png)

![img](https://p.ipic.vip/nxxgm2.png)

![img](https://p.ipic.vip/h4ffa1.png)

![img](https://p.ipic.vip/rizq26.png)

![img](https://p.ipic.vip/g2lq32.png)

![img](https://p.ipic.vip/hi2wvm.png)

![img](https://p.ipic.vip/rtuhi0.png)

![img](https://p.ipic.vip/jt6t98.png)

项目使用到的依赖：

![img](https://p.ipic.vip/9fx9ht.png)

单体配置文件：

```YAML
server:
  port: 8088
spring:
  thymeleaf:
    cache: false
  datasource:
    url: jdbc:mysql://121.4.113.134:3306/shipping_app?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: shiwo2002625
    driver-class-name: com.mysql.cj.jdbc.Driver
  application:
    name: shippingApp
  redis:
    host: 8.130.92.141
    port: 6379
    password: root
    lettuce:
      pool:
        max-active: 10
        max-idle: 10
        min-idle: 1
        time-between-eviction-runs: 10s
    database: 1
  rabbitmq:
    host: 121.4.113.134
    port: 5672
    username: admin
    password: shiwo2002625
    publisher-confirm-type: correlated
    publisher-returns: true
    listener:
      simple:
        #自动签收auto  手动 manual
        acknowledge-mode: manual

  mail:
    host: smtp.qq.com #发送邮件服务器
    username: 268022625@qq.com  #发送邮件的邮箱地址
    password: htayerpbukilcacb  #客户端授权码，不是邮箱密码，这个在qq邮箱设置里面自动生成的
    properties:
      mail:
        smtp:
          port: 465 #端口号465或587
          starttls:
            enable: true
            required: true
          ssl:
            enable: true
    from: 268022625@qq.com  # 发送邮件的地址，和上面username一致
    default-encoding: utf-8
logging:
  level:
    cn.itcast: debug
  pattern:
    dateformat: MM-dd HH:mm:ss:SSS
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-not-delete-value: 0
      logic-delete-value: 1
      logic-delete-field: status
```

使用MyBatis-Plus进行数据库映射与交互：

![img](https://p.ipic.vip/taycpg.png)

Controller层功能划分介绍：

![img](https://p.ipic.vip/j864kx.png)

### **使用Thymeleaf创建****前端**

在本次作业中，首先创建了以下模版，分为管理订单的 order 和用户登录注册及主页面展示的的 user。

- Order
  - `createOrder` 创建订单页
  - `order_result` 查询订单的结果页
  - `queryOrder` 输入查询订单号的页
  - `success` 创建订单的结果页
- User
  - `dashboard` 用户登录成功后进入的页面
  - `login` 登录页
  - `register` 注册页
- `InfoBlock`可以复用的信息块组件

请注意，我们在 Assignment2 中对前端的功能进行了大幅扩展，增加了承运商功能。

下面，将具体介绍具体的数据流转与页面布局。

![img](https://p.ipic.vip/bmag2n.png)

#### 登录 Login

以下是 Login 的静态样式，在点击登录时，请求 

```
<form th:action="@{/user/doLogin}" method="post">
```

该 map 在后端将在后端 session 存入 token 等必要信息，之后跳转到 dashboard 页

![img](https://p.ipic.vip/o7sizw.png)

#### 注册 Register

以下是 Register 的静态样式，在点击登录时，请求 

```
<form th:action="@{/user/doRegister}" method="post">
```

该 map 和登录一样，在后端将在后端 session 存入 token 等必要信息，之后跳转到 dashboard 页

![img](https://p.ipic.vip/i67ru6.png)

#### 用户主页面 Dashboard

后端在返回页面时，将该用户的所有订单全部加入属性，然后在 html 渲染出来：

同时上面放置了2个按钮，点击后可以分别请求后端的两个新页面，并要求返回。

```TypeScript
@RequestMapping("/toQueryOrder")
public String toQueryOrder(Model model){
    List<ShippingOrder> shippingOrderList = shippingOrderService.listOrdersOfConsumer(true, UserAccountPackHolder.getUser().getId());
    List<ShippingOrder> shippingOrderList2 = shippingOrderService.listOrdersOfConsumer(false, UserAccountPackHolder.getUser().getId());
    shippingOrderList.addAll(shippingOrderList2);
    model.addAttribute("order_list",shippingOrderList);
    return "user/dashboard";
}
```

![img](https://p.ipic.vip/38gjlc.png)

#### 新建订单 createOrder

在本页面中，可以用于创建一个运单，将所有的作为表单，然后请求

```TypeScript
<form action="#" th:action="@{/order/create}" th:object="${shippingOrder}" method="post" class="form">
```

对应的请求的代码，将前端发送的数据加入到 model 中，然后返回 success 视图。这个视图会显示当前的内容是否创建成功。

```TypeScript
@PostMapping("/create")
    @ResponseBody
    public String createShippingOrder(@ModelAttribute ShippingOrder shippingOrder,
                                      @RequestParam("latestDeliveryDate")  String latestDeliveryDate,
//                                      @RequestParam("latestDeliveryTime") String latestDeliveryTime,
                                      Model model){
        UserAccountPack user = UserAccountPackHolder.getUser();
        shippingOrder.setConsumerId(user.getId());
        LocalDateTime localDateTime = LocalDateTime.parse(latestDeliveryDate + "T00:00");
        Date latestDeliveryDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        shippingOrder.setLatestDeliveryTime(latestDeliveryDateTime);
        R response = shippingOrderService.createShippingOrder(shippingOrder);
        // 可以将结果添加到模型中，以便在下一个视图中显示
        model.addAttribute("response", response);

        // 返回
        return "/order/success";
    }
```

![img](https://p.ipic.vip/ha900c.png)

#### 查询订单 searchOrder

当跳转进入查询订单时，可以输入订单号，将信息发送给后端：

```TypeScript
<div id="query-order" class="total-css">
    <div class="search-container">
        <input type="text" id="order-input" class="input" placeholder="请输入订单号" />
        <button class="button" onclick="searchOrder()">查询运单</button>
    </div>
</div>

<script>
    function searchOrder() {
        var orderId = document.getElementById('order-input').value;
        window.location.href = '/order/get-order-by-order-id?orderId=' + orderId;
    }
</script>
```

![img](https://p.ipic.vip/e1g27s.png)

#### 查看订单 Order Result

在输入完订单号之后，`/order/get-order-by-order-id?orderId=` 方法会将对应的属性加入

```TypeScript
@GetMapping("/get-order-by-order-id")
public String getOrderByOrderId(@RequestParam String orderId, Model model){
    ShippingOrder response = shippingOrderService.getOrderByOrderId(orderId);

    // 当订单不存在时，设置一个错误消息
    if (response == null) {
        model.addAttribute("errorMessage", "查询失败，请检查号码后重试");
    } else {
        model.addAttribute("orderData", response);
    }

    return "/order/older_result";
}
```

![img](https://p.ipic.vip/hsk4bh.png)

### 使用**Session、Cookies、****拦截器****进行身份验证和授权**

本项目共使用了两种方法

1. 使用AOP进行横切编程：
   1. 使用原因：当业务逻辑都写好后，临时需要添加鉴权功能，在不改变代码的情况，使用AOP很好的解决了这个难题：
   2. ```Java
      @Aspect
      @Component
      public class AuthAspect {
      
          @Autowired
          private HttpSession session;
      
          @Before("execution(* com.xzx.shippingapplication.controller.order.ShippingOrderController.*(..))")
          public void beforeMethod(JoinPoint joinPoint) {
              // 从 Session 中获取 token
              String token = (String) session.getAttribute("token");
      
              // 如果 token 不存在，抛出异常或跳转到错误页面
              if (token == null) {
                  throw new RuntimeException("未登录或登录已过期");
                  //或者
                  //return "error/401";
              }
      
              // 验证令牌
              DecodedJWT verify = JWTUtils.verify(token);
      
              // 将用户信息放到 treadLocal 中
              UserAccountPack userAccountPack = new UserAccountPack();
              String idString = verify.getClaim("id").toString();
              String carrierIdString = verify.getClaim("carrierId").toString();
      
              userAccountPack.setIdentity(verify.getClaim("identity").toString().charAt(1) - '0');
              userAccountPack.setEmail(verify.getClaim("email").toString());
              userAccountPack.setUsername(verify.getClaim("username").toString());
              userAccountPack.setId(Integer.valueOf(idString.substring(1, idString.length() - 1)));
              if (!carrierIdString.equals("Null Claim")) {
                  userAccountPack.setCarrierId(Integer.valueOf(carrierIdString.substring(1, carrierIdString.length() - 1)));
              }
      
              UserAccountPackHolder.saveUser(userAccountPack);
          }
      ```
2. 使用拦截器对请求进行拦截，获取token进行校验：
   1. 配置类：
   2. ```Java
          @Override
          protected void addInterceptors(InterceptorRegistry registry) {
      
              registry.addInterceptor(new TokenInterceptor())
                      .addPathPatterns("/order/**","/carrier/**")
      //                .addPathPatterns("/**")
                      .excludePathPatterns("/user/**")
                      .excludePathPatterns("test.html")
                      .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                      .excludePathPatterns("/city/**")
                      .order(0);
      
              registry.addInterceptor(new OwnerInterceptor())
                      .addPathPatterns("/order/**","/carrier/get-all-carrier-name")
                      .order(1);
      
              registry.addInterceptor(new CarrierInterceptor())
                      .addPathPatterns("/carrier/**")
                      .excludePathPatterns("/carrier/get-all-carrier-name")
                      .order(1);
      
              super.addInterceptors(registry);
          }
      ```

   3. 拦截器：一共使用三个拦截器进行校验，**流程图**如下：

![img](https://p.ipic.vip/xif3z7.png)

## **Assignment2 - 使用****REST API****进行Assignment1的重新设计**

在这个作业中，我们使用REST API和 Axios 重新开发Web应用。

### **Restful** **API**

使用了**Restful** **API**进行**接口**的编写

例子：

```Java
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    AreaProvincialCapitalService areaProvincialCapitalService;

    /**
     *     获取所有城市信息 id 和 名称
     */
    @GetMapping("/All")
    @ResponseBody
    public R getAllCarrier(){
        List<AreaProvincialCapital> list = areaProvincialCapitalService.list(new QueryWrapper<AreaProvincialCapital>());
        return R.ok().data("city",list);
    }
}
```

### **使用****Spring Security****和****JWT****进行****API****身份验证和授权**

1. 引入依赖：

```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

1. 实现**UserDetailsService类，进行登录授权**

```Java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserAccountMapper userAccountMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAccount::getEmail,email);
        UserAccount userAccount = userAccountMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(userAccount)){
            throw new RuntimeException("用户名或密码错误");
        }
        //根据用户查询权限信息 添加到LoginUser中
        List<String> permissionKeyList = new ArrayList<>();
        if(userAccount.getIdentity()==USER_IDENTITY_OWNER)permissionKeyList.add("owner");
        if(userAccount.getIdentity()==USER_IDENTITY_CARRIER)permissionKeyList.add("carrier");

        //封装成UserDetails对象返回
        return new LoginUser(userAccount,permissionKeyList);
    }
}
```

1. 配置文件：

```Java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
            //关闭csrf
            .csrf().disable()
            //不通过Session获取SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // 对于登录接口 允许匿名访问
            .antMatchers("/user/login").anonymous()
            .antMatchers("/user/register").anonymous()
            .antMatchers("/user/get-register-code").anonymous()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated();

    //把token校验过滤器添加到过滤器链中
    http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    //配置异常处理器
    http.exceptionHandling()
            //配置认证失败处理器
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler);

    //允许跨域
    http.cors();
}
```

1. 配置AuthenticationManager

```Java
@Bean
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
}
```

1. 写过滤器，从token中获取身份，写入SecurityContextHolder中方便后续鉴权操作

```Java
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        try {
            DecodedJWT verify = JWTUtils.verify(token);
            String idString = verify.getClaim("id").toString();
//            HashMap<String,String> map = JSON.parseObject(json, HashMap.class);
            userid = idString.substring(1,idString.length()-1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = JSON.parseObject(redisTemplate.opsForValue().get(redisKey), LoginUser.class);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
```

### **应用OpenAPI文档、限流等策略以优化****REST API**

基于Spring Aop技术和注解实现限流。

限流实现代码：

![img](https://p.ipic.vip/o45f9f.png)

要降级的方法进行注解：

![img](https://p.ipic.vip/2y12it.png)

### **使用Axios和Vue.js重新设计Web前端，替换Thymeleaf**

#### 登录与注册

启动系统时，用户可以使用邮箱注册并登录。我们的系统账号分为两种账号：

- 用户账号，可以创建订单、查询订单等
- 承运商账号，可以查看该承运商的货车状态，各个订单的状态以及执行发货操作等

![img](https://p.ipic.vip/muigi9.png)

![img](https://p.ipic.vip/mnuzii.png)

#### 创建订单

在创建订单页面，可以填写货物的信息、寄件人信息及收件人信息。系统将自动根据货物重量以及地区距离（使用百度地图的 API 计算）计算出大致价格。

![img](https://p.ipic.vip/8ppsuw.png)

#### 查询订单

输入运单号，可以查询订单

![img](https://p.ipic.vip/dfb35m.png)

#### 承运商：查看发货状态

当登入承运商系统时，可以查看所有的订单状态以及本承运商的车辆状态（数量）

并可以确认发货及确认到达等操作

![img](https://p.ipic.vip/m4nf2l.png)

#### 承运商：发货

当登入承运商系统时，当点击确认发货时，系统就会查看所有的未发货信息，并展示：

![img](https://p.ipic.vip/cefllt.png)

### 对接口的集成测试

- 测试工具：postman
- 测试端口：localhost:8088

#### 用户模块

1. 获取验证码:

![img](https://p.ipic.vip/fn4e80.png)

![img](https://p.ipic.vip/i79kdx.png)

1. 注册：

![img](https://p.ipic.vip/q8gp2m.png)

1. 登录：

![img](https://p.ipic.vip/ojy1e2.png)

#### （TODO）承运商模块

1. 获取已开通城市列表：

![img](https://p.ipic.vip/agpgi4.png)

1. 获取承运商信息

![img](https://p.ipic.vip/pnobzk.png)

1. *承运商手动发货*
2. *承运商手动到货*
3. *在途运力(还未发车)查询：*

![img](https://p.ipic.vip/h3mxlf.png)

1. 获取在途（途中）运力信息：

![img](https://p.ipic.vip/grrouf.png)

1. 获取在途（到达）运力信息：

![img](https://p.ipic.vip/nnzfnl.png)

1. 获取承运商运力概览信息：

![img](https://p.ipic.vip/x8l0ru.png)

#### 客户订单模块

1. *创建订单*

![img](https://p.ipic.vip/k5kxph.png)

1. *根据订单id返回订单信息*

![img](https://p.ipic.vip/c2td21.png)

1. *列出某个客户的订单*

![img](https://p.ipic.vip/un6hnl.png)

1. *修改某个订单的信息*

![img](https://p.ipic.vip/syg4fc.png)

![img](https://p.ipic.vip/i8spi9.png)

1. *给订单添加物流信息*

![img](https://p.ipic.vip/wa8tk0.png)

![img](https://p.ipic.vip/9vjaqm.png)

1. *获取订单的所有物流信息，默认时间排序*

![img](https://p.ipic.vip/6ccb05.png)

## **Assignment3 - 使用****微服务架构****和****Spring Cloud****进行航开发**

说明：使用Spring Cloud对该项目进行重构。

### **将单体服务重构为****微服务架构**

我们把单体拆成以下几个微服务：

![img](https://p.ipic.vip/gbqsez.png)

### **使用****Eureka****进行服务发现**

![img](https://p.ipic.vip/56m9ho.png)

### **使用Resilience4j或Hystrix实现****断路器**

1.导入Hystrix相关依赖。

![img](https://p.ipic.vip/qr47wc.png)

- 2.对需要限流的方法添加注解（本项目采用信号量限流方式）

-  以下截图来自创建订单方法

![img](https://p.ipic.vip/39tzkz.png)

3.达到限流时的反馈

![img](https://p.ipic.vip/w95k3n.png)

### **使用****Gateway****向外部用户暴露服务**

```YAML
spring:
  application:
    name: gateway
  cloud:
#    nacos:
#      server-addr: nacos:8848 # nacos地址
    gateway:
      routes:
        - id: user-service # 路由标示，必须唯一
          uri: lb://userservice # 路由的目标地址
          predicates: # 路由断言，判断请求是否符合规则
            - Path=/user/** # 路径断言，判断路径是否是以/user开头，如果是则符合
          filters:
            - IgnoreTestGlobalFilter
        - id: order-service
          uri: lb://orderservice
          predicates:
            - Path=/order/**
          filters:
            - IgnoreOwnerAuthorizeGlobalFilter
        - id: carrier-service
          uri: lb://carrierservice
          predicates:
            - Path=/carrier/**,/city/**
          filters:
            - IgnoreCarrierAuthorizeGlobalFilter


      globalcors:
        cors-configurations:
          '[/**]': # 匹配所有请求
            allowedOrigins: "*" #跨域处理 允许所有的域
            allowedMethods: # 支持的方法
              - GET
              - POST
              - PUT
              - DELETE
```

![img](https://p.ipic.vip/zj8huc.png)

### **使用****Spring Cloud** **Config Server和Sleuth进行集中式配置和跟踪**

- 1.添加相关依赖

- ​       服务端：

![img](https://p.ipic.vip/aq9qdy.png)

- ​          客户端:

- ![img](https://p.ipic.vip/hfo2yx.png)

2.在github上创建配置仓库并添加配置文件

![img](https://p.ipic.vip/2wmv99.png)

​      3.增加配置中心应用

![img](https://p.ipic.vip/o8nche.png)

- 4.给需要拉取配置的微服务添加相关配置以实现去统一配置中心拉取配置

-  以用户微服务为例：

-   1)增加bootstrap.yml配置文件

- ![img](https://p.ipic.vip/fn34q9.png)

-   2）注释掉application.yml中原有配置内容

-  注意事项：

-  1.注意github仓库的文件命名要与微服务名称相匹配

-  2.项目中要对github的用户名和密码进行配置，否则会出现权限问题。

## **Assignment4 - 微服务的事件通知**

在这个作业中，添加以下功能进一步改进Assignment3项目：

### **在微服务中使用****kafka****组件**

1.添加kafka相关依赖

![img](https://p.ipic.vip/czce8k.png)

- 2.增加消息发送者与监听者

-  发送者:

- ![img](https://p.ipic.vip/1kfwc8.png)

-   监听者:

- ![img](https://p.ipic.vip/krxa0u.png)

- 3.填写业务逻辑

-  创建订单：

- ![img](https://p.ipic.vip/0esn5w.png)

-  处理订单：

- ![img](https://p.ipic.vip/yv00al.png)

### **集成springboot配置使用服务器进行集中配置。（additional）**

- 1.添加相关依赖

- ​       服务端：

![img](https://p.ipic.vip/01289o.png)

- ​          客户端:

- ![img](https://p.ipic.vip/sd927b.png)

2.在github上创建配置仓库并添加配置文件

![img](https://p.ipic.vip/vhyowv.png)

​      3.增加配置中心应用

![img](https://p.ipic.vip/0wdzza.png)

- 4.给需要拉取配置的微服务添加相关配置以实现去统一配置中心拉取配置

-  以用户微服务为例：

-   1)增加bootstrap.yml配置文件

- ![img](https://p.ipic.vip/03emft.png)

-   2）注释掉application.yml中原有配置内容

-  注意事项：

-  1.注意github仓库的文件命名要与微服务名称相匹配

-  2.项目中要对github的用户名和密码进行配置，否则会出现权限问题。
