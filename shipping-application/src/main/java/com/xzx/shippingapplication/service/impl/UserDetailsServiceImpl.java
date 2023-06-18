package com.xzx.shippingapplication.service.impl;

import com.xzx.shippingapplication.mapper.UserAccountMapper;
import com.xzx.shippingapplication.pojo.LoginUser;
import com.xzx.shippingapplication.pojo.UserAccount;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.xzx.shippingapplication.common.util.Constant.USER_IDENTITY_CARRIER;
import static com.xzx.shippingapplication.common.util.Constant.USER_IDENTITY_OWNER;

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