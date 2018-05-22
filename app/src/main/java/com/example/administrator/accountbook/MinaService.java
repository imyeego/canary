package com.example.administrator.accountbook;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class MinaService extends Service {

    private ConnectionThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ConnectionThread("mina", getApplicationContext());
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disConnection();
        thread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ConnectionThread extends HandlerThread{

        private Context mContext;
        private boolean isConnected;
        ConnectionManager manager;

        ConnectionThread(String name, Context context){
            super(name);
            this.mContext = context;
            ConnectionConfig config = new ConnectionConfig.Builder(context)
                    .setIp("192.168.1.102")
                    .setPort(12345)
                    .setReadBufferSize(4096)
                    .setConnectionTimeOut(1000)
                    .builder();

            manager = new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();

            for (;;){

                isConnected = manager.connect();

                if (isConnected){
                    break;
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void disConnection(){
            manager.disconnect();
        }
    }
}
