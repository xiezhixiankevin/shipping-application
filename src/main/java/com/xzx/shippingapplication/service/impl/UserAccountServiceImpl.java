package com.xzx.shippingapplication.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.shippingapplication.common.util.JWTUtils;
import com.xzx.shippingapplication.mapper.UserAccountMapper;
import com.xzx.shippingapplication.pojo.UserAccount;
import com.xzx.shippingapplication.pojo.pack.UserAccountPack;
import com.xzx.shippingapplication.service.UserAccountService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-25
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Override
    public UserAccount registerIntoTable(UserAccount userAccount) {
        try{
            save(userAccount);
            return userAccount;
        }catch (DuplicateKeyException e){
            // 邮箱唯一
            return null;
        }
    }

    @Override
    public UserAccountPack register(UserAccountPack userAccount, RedisTemplate<String,String> redisTemplate) {
        String code = userAccount.getCode();
        String email = userAccount.getEmail();

        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String realCode = forValue.get(email + "-register-code");
        if(realCode ==null){
            return null;
        }else if(realCode.equals(code)){
            // 验证码正确
            if(registerIntoTable(new UserAccount(userAccount)) != null){
                userAccount.setPassword("");
                userAccount.setCode("");
                return userAccount;
            }
            // 已经注册过账号
            return null;
        }else return null;

    }

    @Override
    public UserAccountPack login(String email, String password) {
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        queryWrapper.eq("password",password);
        UserAccount userAccount = getOne(queryWrapper);
        if(userAccount != null){
            // 生成token
            Map<String,String> map = new HashMap<>();
            map.put("email",email);
            map.put("username",userAccount.getUsername());
            map.put("identity",userAccount.getIdentity().toString());
            map.put("id",userAccount.getId().toString());
            if(userAccount.getCarrierId()!=null)map.put("carrierId",userAccount.getCarrierId().toString());
            String token = JWTUtils.getToken(map);

            UserAccountPack userAccountPack = new UserAccountPack();
            userAccountPack.setEmail(email);
            userAccountPack.setUsername(userAccount.getUsername());
            userAccountPack.setIdentity(userAccount.getIdentity());
            userAccountPack.setToken(token);
            return userAccountPack;
        }
        return null;
    }
}
