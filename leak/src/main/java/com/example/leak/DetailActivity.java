package com.example.leak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class DetailActivity extends AppCompatActivity {

    private Button btn;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        handler = new MyHandler(this);
        btn = findViewById(R.id.btn_click);
        btn.setOnClickListener(v -> {
            handler.sendEmptyMessageDelayed(0, 30000);

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        BaseApp.getRefWatcher().watch(this);
    }

    //静态内部类使用弱引用防止内存泄露
    private static class MyHandler extends Handler{
        private WeakReference<DetailActivity> activityWeakReference;

        public MyHandler(DetailActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("e", "receive msg");

        }
    }
}
