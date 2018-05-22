package com.example.mvpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity<TimelinePresener, TimelineView> implements TimelineView{

    private TextView tv;
    private Button btn;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                tv.setText((CharSequence) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv_text);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeline();
            }
        });


    }

    private void getTimeline(){

        getPresenter().getTimeline();
    }

    @Override
    public TimelinePresener createPresenter() {
        return new TimelinePresener();
    }

    @Override
    public TimelineView createView() {
        return this;
    }

    @Override
    public void onTimelineResult(String result) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = result;
        handler.sendMessage(msg);
    }


}
