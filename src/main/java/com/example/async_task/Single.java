package com.example.async_task;

/**
 * 饿汉单例
 */
public class Single {

    private static Single instance = new Single();

    public static Single getInstance(){
        return instance;
    }
}
