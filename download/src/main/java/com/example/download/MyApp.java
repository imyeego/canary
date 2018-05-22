package com.example.download;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class MyApp extends Application {

    public static Context context; //全局context对象
    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = this;
    }

    public static MyApp getInstance(){
        return app;
    }
}
