package com.vector.study.netty.test4.pojo.model;

import java.text.SimpleDateFormat;

/**
 * author: vector.huang
 * date：2016/4/16 18:04
 */
public class TimeModel {

    private long timeMillis;

    public TimeModel(){
        this(System.currentTimeMillis());
    }

    public TimeModel(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public long timeMillis() {
        return timeMillis;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(timeMillis);
    }
}
