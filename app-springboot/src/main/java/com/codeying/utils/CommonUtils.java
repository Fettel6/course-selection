package com.codeying.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 常用工具类
 */
public class CommonUtils {

    private static final AtomicInteger n = new AtomicInteger(100000);

    /**
     * 获取随机id，根据时间和五位随机数
     * @return
     */
    public static String getRandomIdByTime() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        int num = n.getAndIncrement();
        return str+num;
    }
    //获取Id
    public static String newId(){
        return getRandomIdByTime();
    }

}

