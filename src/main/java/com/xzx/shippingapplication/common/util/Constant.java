package com.xzx.shippingapplication.common.util;


import java.util.HashMap;

//@Component
public class Constant {
    public static final int CITY_BEIJING=1;

    public static final int CITY_SHANGHAI=2;

    public static final int CITY_KUNMING=3;

    public static final int CITY_GUANGZHOU=4;

    public static final int CITY_FUZHOU=5;

    public static final int CITY_WULUMUQI=6;

    public static final int CITY_GUIYANG=7;

    public static final int CITY_XIAN=8;

    public static final int CITY_NANCHANG=9;

    public static final HashMap<String,Integer> CITY_TO_ID_MAP;
    static {
        CITY_TO_ID_MAP=new HashMap<>();
        CITY_TO_ID_MAP.put("北京",CITY_BEIJING);
        CITY_TO_ID_MAP.put("上海",CITY_SHANGHAI);
        CITY_TO_ID_MAP.put("昆明",CITY_KUNMING);
        CITY_TO_ID_MAP.put("广州",CITY_GUANGZHOU);
    }

    //运力的当前状态
    public static final int TRANSPORTATION_STATUS_WAITING =1;   //等待发车
    public static final int TRANSPORTATION_STATUS_IN_TRANSIT =2;//正在路上
    public static final int TRANSPORTATION_STATUS_FINISH =3;    //已经送达

    public static final int TRANSPORTATION_REFRIGERATED =1;//运力有冷冻功能
    public static final int TRANSPORTATION_NOT_REFRIGERATED =0;//运力没有冷冻功能

    //运力的类型
    public static final int TRANSPORTATION_TYPE_SMALL_TRUCK =1;//小货车
    public static final int TRANSPORTATION_TYPE_BIG_TRUCK =2;//大货车
    public static final int TRANSPORTATION_TYPE_AIRCRAFT =3;//飞机

    //运力的最大承载量
    public static final double TRANSPORTATION_MAX_CAPACITY_SMALL_TRUCK =3000;//小货车
    public static final double TRANSPORTATION_MAX_CAPACITY_BIG_TRUCK =18000;//大货车
    public static final double TRANSPORTATION_MAX_CAPACITY_AIRCRAFT =100000;//飞机


    //用户身份
    public static final int USER_IDENTITY_OWNER =0;//货主
    public static final int USER_IDENTITY_CARRIER =1;//承运商
    public static final int USER_IDENTITY_ADMIN =2;//管理员




}
