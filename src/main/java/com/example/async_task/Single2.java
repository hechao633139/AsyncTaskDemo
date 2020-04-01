package com.example.async_task;

import android.content.Context;

/**
 * 懒汉
 */
public class Single2 {

    public static Single2 instance;
    private Context context;

    public Single2(Context context) {
        this.context = context;
    }

    public static Single2 getInstance(Context context){
        if (instance == null){
            synchronized (Single2.class){
                if (instance == null){
                    instance = new Single2(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
}
