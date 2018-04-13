package com.no1woker.common;

/**
 * Created by YangKai on 2017/6/15.
 */
public class Const {
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    public interface RedisCacheExtime{
        //30分钟
        int REDIS_SESSION_EXTIME = 60 * 30;
    }
}
