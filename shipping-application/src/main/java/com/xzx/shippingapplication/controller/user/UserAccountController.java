package com.xzx.shippingapplication.controller.user;


import com.xzx.shippingapplication.common.R;
import com.xzx.shippingapplication.common.util.EmailUtils;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import com.xzx.shippingapplication.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
//@RestController
@RequestMapping("/user")
@Controller
public class UserAccountController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    EmailUtils emailUtils;


    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }

    @RequestMapping("/doLogin")
    public String login(@RequestParam String email,
                   @RequestParam String password,Model model){
        UserAccountPack userAccount = userAccountService.login(email, password);
        if(userAccount != null){
            String token = userAccount.getToken();
            userAccount.setToken(null);
            //登陆成功
            model.addAttribute("token",token);
            model.addAttribute("user_info",userAccount);
            return "redirect:/用户登录后的页面";
        }
        model.addAttribute("info","密码错误或用户不存在");
        return "user/login";
    }

    /**
     * 注册界面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "/user/register";
    }

    /**
     * 判断是否成功注册
     * @return
     */
    @RequestMapping("/doRegister")
    public String register(@RequestParam UserAccountPack userAccountPack,Model model){
        // 校验验证码
        UserAccountPack userAccount = userAccountService.register(userAccountPack,redisTemplate);
        if(userAccount != null){
            //注册成功
            model.addAttribute("user_info",userAccount);
            return "redirect:/注册成功后的页面";
        }
        model.addAttribute("info","注册失败!验证码错误或已有账号");
        return "user/register";
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

