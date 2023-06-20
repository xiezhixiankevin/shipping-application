# **作业文档**

组员：黄泽霖、韩楚博、谢志贤

**这里将介绍四个作业的交付物，它们分别位于Assignment1、Assignment2、Assignment3和Assignment4四个分支中。**

请注意，这个文档正在迁移到GitHub上。

tips：本次作业，通过代码截取、流程图、时序图等方式展示出来。

## **项目背景及概览**

​      本项目实现了一个货运管理系统。系统涵盖了**承运商**和**客户**两个主要角色，并为他们提供了一系列功能。

​      对于客户而言，他们可以通过系统新建订单、查询订单状态和查看个人订单。在新建订单过程中，客户可以根据自己的需求选择不同的承运商，并可根据特定要求进行个性化选项的配置。这样，客户可以根据运输需求、时间要求和预算等因素，灵活选择最适合自己的承运商和服务。

​      而对于承运商来说，该系统提供了订单管理功能，使其能够高效地处理和管理所有属于本承运商的订单。承运商可以通过系统进行发货和到货操作，更新订单状态。此外，本系统还允许承运商管理自己的运输工具，如飞机、大中小型卡车等。

## **项目技术栈**

**后端：**

- 基础框架：Spring Boot、Spring MVC、MyBatis-Plus
- 升级框架：Spring Cloud、Spring Security
- 基本组件：Eureka、Gateway、Feign、MySQL、Redis、Kafka、Hystrix、Lombok、Fastjson、Swagger、Rabbitmq
- 服务器：阿里云2核1g云服务器、腾讯云4核4g服务器

**前端：**

- Thymeleaf
- Vue.js
- ElementUI

**项目管理：**

- GitHub
- Git
- Lark

## （TODO）系统架构图

## （TODO）系统功能图

## **Assignment1 - 使用****Spring MVC****进行运输****Web应用****开发**

### **创建和配置****Spring MVC****项目、整合Spring Data JPA/Mybatis-Plus**

数据库表详情：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGM2OTVjODk5OWJiNDkyN2U0MmI1N2FhN2QxNmFjZTVfS29aMjdBZUZMblA0eERZamd0UjNlY1MyNjU1UVVOUDFfVG9rZW46SmdjOWJNOE5hb0lndnZ4REZTWWNuN1I2bkJlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OWFiZWY1MWVjOWZiNTNiYTczNjUyZGM3OGE2ODliMGJfMFhHUkRuVDY4em1FSmUzUWduUnI0dlpkemNoZ21CNWdfVG9rZW46VXo2SWJ1bExFb0NBYlV4bHE1c2NtZmJxbjRnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NDY3YzIyOGE1ZTA5MDZiODFjNmMxZGUzMDUyYzQ2NTVfZVN4ZWlxOWlYQlhKQTVrSElybDhTY0lTU1JEQ3l0MlZfVG9rZW46RWxWRmJrVGszb2NEU3Z4SUhyb2NZRlJ6bk1rXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MTVjMTAzZmJiZGE2ZTA1MmYxZDQ5ODNiMzM0YWUyZmRfaFlQZHNrZFVSTFpJTHFiZnY0dmtoYUlTMWJsaXJRSWVfVG9rZW46TVExU2Jaa1BXb2N1eTh4SGNiU2NCYW94bm1lXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDg5NzhlNTZkZTRiZjcxZjE4NmEyNWYyM2FjY2E0ZDRfVWNmNzRlT1hGZGg0d1hqTWM1czZrWlNWSEJBMjhOb2ZfVG9rZW46Q3Exb2J6Z3FKb0p4NEF4aHBGUmNKdnFUbnZJXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MjZlZDViMTI5ODU1MTY3NzQ2ZDU5MDMyNmNmZjliNWRfOFJUa0hsRXBBMk1nR1lubXNFZXJJUmRCSjBvSDdyQTFfVG9rZW46VnF4UmJIbVB1b2J2YVp4a011MmNKSllVbkxkXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OTdjOWEyOThlNTg5Y2NiNjM3ZTQ0NWI1YTY1ZGVjNjBfSDd0VkN5Y0tQZlNKaVZacWhZeWVuTzlYMlpIZHlwSmdfVG9rZW46TUdVQmJLaUZLb1J2TDd4QnJyWmN2MzB4bnFmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGM2ZTQwZjZjYmViMTE2N2JlYzFhNjJjOTNjNjc4NzVfanAxdHB2cmtZSEIxOU9yeHc3VTY3NlpKa0NWNXR6TzhfVG9rZW46S3NGNWJ4SExXb29SeU94QVlTWGNSeHFibkhiXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MTI0ZDhkMzhmYWFlYWI0YjNlNjlmYWVlZDVhYTMxM2RfZnNsaHZDaW5zUFZmME5jY3IycTVxSjhLWmFMR0xwQnpfVG9rZW46SU11RGJjSmJ1b3JrZ3B4VFlYSGNEdWpHbm9mXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

项目使用到的依赖：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTFkOTY3NmFhZThiMzdlYjUzNWZlNzg5YzE1NGU2NTNfTG9jdUFaQ084aEE4bzRuMmFKYUF3QmRBZnJNMFBIeHFfVG9rZW46SmRLZWJGejdVb2dUdzN4TU9IWWNuNUVvbkpkXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

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

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MzAwZTU3YmJjMzU2NzRlZjU4ZTNkMTIzODdkNDk0OWNfY0ZnVVV2ZXhVZTFubThzeVh5T0g1bmdEeThsRkJscHJfVG9rZW46UGc1QWJYelI2b1dDRVJ4bnpFRmNyU1F0bnJmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

Controller层功能划分介绍：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OGYxZWJiNjAyNmYwMjNiNDllMzllM2NkYWZjZjY4YTdfRmY2OHVNOGVJQ0RNT2k3dDdMT0I5S2FHMW9aaXZWSGJfVG9rZW46UGhmSGI4VEdPb256bzB4RXZxUmNPT29BbkVlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

### （TODO）**使用Thymeleaf创建****前端**

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
- `InfoBlock`

下面，将具体介绍具体的数据流转与页面布局

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=N2E4NmNkMjhlZGNmZTI3MWE3YzY3MTUyMjQzZjJhYjBfNjJjeGk1MXBaVEVpcFV1V0laeTV2YnNrQkFFWEFJMTFfVG9rZW46RWhsOWJOVEw3b01QZTJ4ZGVCMmN0cE5tbnRIXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### Login

以下是 Login 的静态样式，在点击登录时，请求 

```
<form th:action="@{/user/doLogin}" method="post">
```

该 map 在后端将在后端 session 存入 token 等必要信息，之后跳转到 dashboard 页

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=YTRlMGVhNDM4NjQwZmE2ZGNjNzE4NDI5MDYyNTk4NmRfeVFXRmtsSjBsUFQzZ3cxWXQ2NE5GYnBKcDRIbEZSdG9fVG9rZW46RURwd2JGejRGb2Fqb2V4M3g4cGNnMnE3blJkXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### Register

以下是 Register 的静态样式，在点击登录时，请求 

```
<form th:action="@{/user/doRegister}" method="post">
```

该 map 和登录一样，在后端将在后端 session 存入 token 等必要信息，之后跳转到 dashboard 页

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=Y2U2YzljYmYxZmNhZWMxY2FlYTQ0NjAzZTA5MGIwOWZfcVZTTGlOM0daM0tCQjQ2WlZLVnhudE5ESG9pMldISFVfVG9rZW46RzhIMWJTN0JCb0oycU14cVcyTWNEcmtHbmtlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### Dashboard

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

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MWJhNzcwOWEzOTJmMTkzODU1YjM3MWQ1ZWI4YWJmY2ZfYWVuM2hkZmpXTUtsZ1l1YTVjRUlXRjNyRUxONXk3YWNfVG9rZW46RFhjOWJ1TnpHb1l1dXN4emQ2UmN3UlJVbjhnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### createOrder

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

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MmMwZDdjNTc5NDJmZDM1MDhkZTk1NjI0YjFkMzJkMTNfY0k2SEtLYXRrZmZkTjlNNnRZajFEU1EzQ0sxbnBnUDRfVG9rZW46STFJdGJiYmdEb2ZiWXJ4NzEwRWNlQndjblZmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

### （TODO）使用**Session、Cookies、****拦截器****进行身份验证和授权**

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

### （TODO）业务逻辑

1. 用户登录注册接口
   1. 

1. 客户接口
   1. 

1. 承运商接口

### 接口展示

1. 
2. 
3. 

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

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGZiYzEwYTRjM2QyYWVmMThlMWRlZDZmMDJlYWEzYWVfZUNOM204amVTbVVrbGYxa3I5NlBERU9rcWQ2M0J5VUFfVG9rZW46QlJOUGJTMk9rb2k0clN4MXB1WGNkekZabmxmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

要降级的方法进行注解：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MGQwZTIwOGNmNWZjMDY5ZWZlZjZiYmExMGVjNmEwNzZfR1dYY2Jic2g0UzdVaHBaMWF2d1J4d0ZKNXpWa1RqSVVfVG9rZW46TkNZZWJCNGJobzFudEt4azlKcmNieW9RbnJnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

### **（TODO）使用Axios和Vue.js重新设计Web前端，替换Thymeleaf**

### 对接口的集成测试

- 测试工具：postman
- 测试端口：localhost:8088

#### 用户模块

1. 获取验证码:

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NTkzOThjN2M3Zjg0MTY5NGE2ZGM0M2E1MDAxZThlM2JfNU9hdHd6NG9PNTJIWDF3cVIyTWlyUnJxa2tjOUo3amZfVG9rZW46U2k4WWJDb0dWb1NuYUF4R2hoeWNNR0JIbkxkXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=YjViYWI1OTI0ZDg5NDYyYzk2MjcyYzY5NjBiNDUwZTRfbWNWaTR5OU5kMFd4QXdwSHZGWnR4bE0xaExVV3FjRWlfVG9rZW46VjVkQmJXdW1Sb0k0UFN4Rm9vVWNnajF3bkhlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. 注册：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OWQ0YTgyNjZhMDgxZDkxMzI2NDA3MTQ5OTljYjBmMjZfOHAxQ1Vxc0VGMEF2d3lVYU14Tnl3U2drSjFYUU02a0dfVG9rZW46QkVzbmJHY0ZTb09ndEp4aHJqVGNEekpobm5kXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. 登录：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ODQ0YzAyNDQ2NWExZGI2YzcyMWMzOTI0OGUyMjA4ZDRfUFJNcEdoek81cE55NDYxM25hclNmZTFZMzlsV3c4dTVfVG9rZW46WkNBWGJDT3hTb3REQlp4T3FpWmNBN3BNbkRiXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### （TODO）承运商模块

1. 获取已开通城市列表：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NWQ0MmY3NTc2YmFlMjQ5ZWViOWEwNWQ2NTRiYmM4NDRfZ0dVTFl0N1l6bzdDbXlYVXdhaDNUMVVvSVlqN1IxUFBfVG9rZW46UGRpc2JhanN2b2FsVG94cnNwQ2NTZTNvbnZnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. 获取承运商信息

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NGNjZjUwZjkwYzlkMTE1YzRkMzFhZTU2NTIyZTgyZjRfU1hteDVJQzBMRFRweExVR21oYXdtY09iczlaZHJER0xfVG9rZW46VkhRZ2I4cFA5bzVQc1h4Tk5JSmNXY2ZubnplXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. *承运商手动发货：*

1. *承运商手动到货：*

1. *在途运力(还未发车)查询：*

1. 获取在途（途中）运力信息：

1. 获取在途（到达）运力信息：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NTA3NmFkMmNhYzg2OWQyYzFkYzc3MDcwMGY5MmFmNWRfSjRJTEtGdzVrbkVTUHV6QzhNTE5YRTdvODU4Sk9jcHFfVG9rZW46Tk9TYWJUc1lxbzFFY3J4RUhxSmM1TlYxbnhmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. 获取承运商运力概览信息：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MjFmNTZmOWRkNmU5ZjYwZDYwZjc2OGEwNzNmMjFlOTRfQld5TkRCNVR4UVU1T3JNSTFRQWpDU3h6OUt2aG9lR0lfVG9rZW46V1FEVWJBUGxZb2lUZjh4ZWhYb2NtOFR0bmZmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

#### （TODO）客户订单模块

1. *创建订单*

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=YzY1ODU1YmM4YWJlZjZlMjIwNDhkMjkwYWM0MjY2YjBfOVJ2WkROa0FNd1VrTmxseG5GZjk0NjdQSUs1SnJscXpfVG9rZW46SXBsb2J1cDdpb1dxc0R4bUNNeWNJS0ZYbnNiXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

1. *根据订单id返回订单信息*

1. *列出某个客户的订单*

1. *修改某个订单的信息*

1. *给订单添加物流信息*

1. *获取订单的所有物流信息，默认时间排序*

## **Assignment3 - 使用****微服务架构****和****Spring Cloud****进行航开发**

说明：使用Spring Cloud对该项目进行重构。

### **将单体服务重构为****微服务架构**

### **使用****Eureka****进行服务发现**

### **使用Resilience4j或Hystrix实现****断路器**

1.导入Hystrix相关依赖。

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=YjExYjlhYzIzZjgwNmQxY2EyNmYyNTQzMDQxNjE1MWNfWFBKZFFQVVQ2VmV6M0wwbW5JWTc5WFhtVUozc1VuenVfVG9rZW46SHpIQWJ5STZSb3R5dFl4clpGN2NzeWxibmRnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- 2.对需要限流的方法添加注解（本项目采用信号量限流方式）

-  以下截图来自创建订单方法

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MDhkOGM2OGVjNzczNmZkOTk2MzExNzlmZDZlYjY2NjdfRklKbk1ybDFHQXlNTlk2Qk1iblZnRFhxMll3eEZFRjhfVG9rZW46SjJFamIzZHM3b2ZWT3F4UEdKNmNvMEtQbjNZXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

3.达到限流时的反馈

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MGNiODhlOTE2MmIyOTU5ZWY2MWI5YTM3OWZjOTJiZmNfMkw2VVJFZDdYU3I2RmM3akpkWVdGV2JPZU95N0phMmRfVG9rZW46Q1ZJOWJzMzg3b002NFh4YjZvcGMwaVVkbkJlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

### **使用****Gateway****向外部用户暴露服务**

### **使用****Spring Cloud** **Config Server和Sleuth进行集中式配置和跟踪**

- 1.添加相关依赖

- ​       服务端：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MWE0YmRjMmIzZGIzMjY0MWFlMGFmNTA2NGI1MGIxZDNfVDY1aTNrdlFkSHE0S3BuRFNRMGc5VjluWUt1M3NycVBfVG9rZW46QnBwZWJNS1A5b0tBa1h4MzRFWmNmdjBBbjliXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- ​          客户端:

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NTViYmI0YjNmYWE1ZTY3MzAzOTkzM2JiYmE5YmJkZjBfaVpWTjk0S0lMRk03bmRaRWRVcUpzdmRqRFhUTkl1dFdfVG9rZW46TTNlTGJTU01Wb1psRXl4TGp0RGNUQTFnbmNjXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

2.在github上创建配置仓库并添加配置文件

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MTQ2ODE5NjhiNWRiMDliNjkxMmU3YzFjZWYwNjE3Y2ZfV2h0ZGFYT3lOTjRZcU8yN2RDWVNIVDRzbDZTN1NnOGpfVG9rZW46T2FueWJhbkRib1FqeVp4dE03bWNXZm9abjRiXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

​      3.增加配置中心应用

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjNlZTNmZjM2MmMxYjRhMjMwOGQ4NGY0NjA5ODdkYmZfbUhqcU5pVG9kMGZRRmJUeWdEdTZZSGhMeXFFVHdVWVNfVG9rZW46SWhDWWJXNXZub0FjMGx4NkI5a2NRb1RGblBoXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- 4.给需要拉取配置的微服务添加相关配置以实现去统一配置中心拉取配置

-  以用户微服务为例：

-   1)增加bootstrap.yml配置文件

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NGU1OGFkNjYyYWFmODNkZDNlZTkwYTBmMDkwMTEwMTBfaWVrRDZTRjhSSm90cDU4UXZkdlg5SE9WS3RUSFlEV3VfVG9rZW46UUlaYmJqQkdWb250V254NDd1S2NrY0FibjhjXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

-   2）注释掉application.yml中原有配置内容

-  注意事项：

-  1.注意github仓库的文件命名要与微服务名称相匹配

-  2.项目中要对github的用户名和密码进行配置，否则会出现权限问题。

## **Assignment4 - 微服务的事件通知**

在这个作业中，添加以下功能进一步改进Assignment3项目：

### **在微服务中使用****kafka****组件**

1.添加kafka相关依赖

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MWNiNDU4YjhhNjA3M2Y5OTBmMzk3MTUyMTk3Yjg4MDdfRkRqd2xuZ1V6ejdDM1BYdXllV2ZDZTFZWnBPS0hjOXJfVG9rZW46RUxSZ2JQRXZzbzhUZVd4emo2UmMydjR6bklmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- 2.增加消息发送者与监听者

-  发送者:

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OGE0OTc3NzNjOTBjNjAyMzU0NzJjYTk3MDViMTgxNjlfclZFWVY0clNwOWtRN1Z4YUI0MmNua2ZRYVFPQTlXY2xfVG9rZW46S1JOYmJ6bEl2b1Q5ZlJ4bmVLcWN4UW5vbjNiXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

-   监听者:

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MmMwMjI2OTY5MWEyZTUxODljZTBjYTg1Mjc0MzhmMGRfbVB6SE1xNUlyRDRQU0FWRzIwWWZ4RzNEdW5Bd0Z0TFFfVG9rZW46WnphR2JEajhRb1k1OXN4S05waWNuUW9sbjFnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- 3.填写业务逻辑

-  创建订单：

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OTQ4ZTQyZjM4ZTAwZjA1YTE3ZWIyMDU4ZTcxYzc5NmJfV2xhSUt0a3hKNTdkUkJMYTdrNlpZeXFXcjNKQWdkQ0hfVG9rZW46WjNQcWJvWGlDb05CS3B4U3E1YWMwRjdrbjZnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

-  处理订单：

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MTMxYzU0YzY1NjQwNzRhZDg3YTkyYzdjYzMwMzYzNDBfd2RiZzNVNGJTS2QyYlNEUXVGRUhDUG1EZDV1d29XMWJfVG9rZW46QXlkQWJETXN2b2d4SkZ4bk1pWmNKOWgxbnNoXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

### **集成springboot配置使用服务器进行集中配置。（additional）**

- 1.添加相关依赖

- ​       服务端：

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ODNiNmMyYzVkNzBlZGNhZjZjNGUwNTJhMDc0MWE3OGVfMWhQVVZxOEtpQWFXOG1aRXhCczRsYktDN3VERnQ3UktfVG9rZW46R2FuMGI3T3BybzRuWnB4OHg3YWNlUmp2bkFlXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- ​          客户端:

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=MzNiYWE3NzlmNmM0MzZhNGY0N2VjMmY5NzhiYjQ2ZDlfMHM0R2xyemFKVXFJelNxWU9NSFJROUZ1S2Z2bEhIUmlfVG9rZW46WXpMTGJDbHVvb2ZhNkV4OERVUmNsQURPbjRmXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

2.在github上创建配置仓库并添加配置文件

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=NTY0Y2MyZmU2ZjY0NzgwODVhZTRiZGUwZjRmMzYyYjlfWEdqVndWbzJUR2pCRmswYzFzTmdXSkJJSnJ1Q0trekJfVG9rZW46RGJhSGI4Z0kzb0hGY0V4SWhQNGM1bko4blFoXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

​      3.增加配置中心应用

![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=ZmE3ZjNiMmJjMjUyNmY4ZjhkOGQ3MjRmMWEyOGU4YmZfNXFFQWVwTkdiVEs1N1F2S2VDV1lCZ3hTRGg5Q29DMFRfVG9rZW46QW43b2J5ZFlBb1hTYnF4QVNLbGNTcDNtbjJnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

- 4.给需要拉取配置的微服务添加相关配置以实现去统一配置中心拉取配置

-  以用户微服务为例：

-   1)增加bootstrap.yml配置文件

- ![img](https://cca6t77neh.feishu.cn/space/api/box/stream/download/asynccode/?code=OGI3MGVjNGMxNjg4MDBlNDlmZGViZjYyMmY1NmZiMTBfZU5TbnNPZWR5SEI3UFhOa3BYUjB1TXUxN0cxMGhJRnlfVG9rZW46TWR0Q2J5SUlPb0lOMXR4TVlVTGNmSmlLbjdnXzE2ODcyNzY2MjQ6MTY4NzI4MDIyNF9WNA)

-   2）注释掉application.yml中原有配置内容

-  注意事项：

-  1.注意github仓库的文件命名要与微服务名称相匹配

-  2.项目中要对github的用户名和密码进行配置，否则会出现权限问题。

### **Spring Cloud** **Sleuth或Zipkin进行****分布式****日志跟踪。（additional）**

