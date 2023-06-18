package com.xzx.shippingapplication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.shippingapplication.pojo.UserAccount;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzx
 * @since 2023-04-25
 */
public interface UserAccountService extends IService<UserAccount> {
    // 注册方法
    UserAccount registerIntoTable(UserAccount userAccount);
    UserAccountPack register(UserAccountPack userAccount, RedisTemplate<String,String> redisTemplate);
    // 登录方法
    UserAccountPack login(String email,String password);
}
