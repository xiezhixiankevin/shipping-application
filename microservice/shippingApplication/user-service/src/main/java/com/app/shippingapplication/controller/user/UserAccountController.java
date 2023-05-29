package com.app.shippingapplication.controller.user;


import cn.itcast.feign.common.R;
import com.app.shippingapplication.util.EmailUtils;
import com.app.shippingapplication.pojo.pack.UserAccountPack;
import com.app.shippingapplication.service.UserAccountService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzx
 * @since 2023-04-25
 */
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    EmailUtils emailUtils;

    @PostMapping("/login")
    public R login(@RequestParam String email,
                   @RequestParam String password){
        UserAccountPack userAccount = userAccountService.login(email, password);
        if(userAccount != null){
            String token = userAccount.getToken();
            userAccount.setToken(null);
            return R.ok().data("user_info",userAccount).data("token",token);
        }
        return R.error().message("用户名或密码错误！或者系统限流请稍后重试");
    }

    @PostMapping("/register")
    public R register(@RequestBody UserAccountPack userAccountPack){
        // 校验验证码
        UserAccountPack userAccount = userAccountService.register(userAccountPack);
        if(userAccount != null){
            return R.ok().data("userInfo",userAccount);
        }
        return R.error().message("注册失败!验证码错误或已有账号");
    }

    private static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    @HystrixCommand(
            groupKey = "timeline-group-rcmd",
            fallbackMethod = "codeFallBack",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"), // 信号量隔离，因为业务方法用了ThreadLocal
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"), //超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="50"),//触发熔断最小请求数量
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="30"),//触发熔断的错误占比阈值
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="3000"),//熔断器回复时间
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value="300"),// 单机最高并发
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value="100")// fallback单机最高并发
            }
    )
    @GetMapping("/get-register-code")
    public R getRegisterCode(@RequestParam String email){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if(valueOperations.get(email + "-register-code") != null){
            return R.error().message("验证码已发送且未过期，请勿反复点击！");
        }
        String code = randomCode();
        valueOperations.set(email + "-register-code",code);
        // 有效时间6分钟
        redisTemplate.expire(email + "-register-code", 6*60*1000, TimeUnit.MILLISECONDS);
        emailUtils.sendSimpleMail(email,
                            "ShippingApp注册验证码",
                            "您的验证码是:"+code + "。请勿泄露验证码，有效时间6分钟。");
        return R.ok().message("验证码已发送，请查看邮箱！");
    }

    public R codeFallBack(@RequestParam String email){
        return R.ok().message("系统限流中，请稍后重试。。。。");
    }
}

