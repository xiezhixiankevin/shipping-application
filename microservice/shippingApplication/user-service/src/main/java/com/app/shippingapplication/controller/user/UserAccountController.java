package com.app.shippingapplication.controller.user;


import com.app.shippingapplication.common.R;
import com.app.shippingapplication.util.EmailUtils;
import com.app.shippingapplication.pojo.pack.UserAccountPack;
import com.app.shippingapplication.service.UserAccountService;
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
        UserAccountPack userAccount = userAccountService.register(userAccountPack,redisTemplate);
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
}

