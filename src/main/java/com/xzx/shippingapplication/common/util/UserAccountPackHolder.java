package com.xzx.shippingapplication.common.util;


import com.xzx.shippingapplication.pojo.pack.UserAccountPack;

public class UserAccountPackHolder {
    private static final ThreadLocal<UserAccountPack> tl = new ThreadLocal<>();

    public static void saveUser(UserAccountPack user){
        tl.set(user);
    }

    public static UserAccountPack getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
