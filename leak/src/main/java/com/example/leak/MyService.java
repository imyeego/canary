package com.example.leak;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class MyService extends Service {

    private static final String TAG = "liuzhao";



    @Override
    public void onCreate() {
        Log.i(TAG,"MyService is oncreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BinderServiceStub();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service of DetailActivity is created: ");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"OnDestory");

    }
}
