package com.example.leak;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class BaseApp extends Application {

    private final static String PROCESS_NAME = "com.example.leak";

    private RefWatcher mRefWatcher;
    private static BaseApp app;

    public static RefWatcher getRefWatcher(){
        return app.mRefWatcher;
    }

    public static BaseApp getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        if (isMainProcess()){
            mRefWatcher = LeakCanary.install(this);
            Log.e("process msg", "Leak Watch has been binded");
        }
    }


    private static String getAppNameByPid(Context context, int pid){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo info: manager.getRunningAppProcesses()){
            if (info.pid == pid){
                return info.processName;
            }
        }

        return "";
    }

    private static boolean isMainProcess(){

        try {
            int pid = Process.myPid();
            String process = getAppNameByPid(BaseApp.getInstance(), pid);

            if (TextUtils.isEmpty(process)) return true;
            else if (PROCESS_NAME.equalsIgnoreCase(process)) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

}
