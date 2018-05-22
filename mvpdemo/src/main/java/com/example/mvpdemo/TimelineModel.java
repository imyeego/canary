package com.example.mvpdemo;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class TimelineModel {

    private String url = "http://192.168.0.103:8080/helloworld/timeline";


    public void getTimeline(final ICallback callback){
        OkAction.get(url, new ICallback() {
            @Override
            public void seccess(String response) {
                callback.seccess(response);

            }
        });
    }
}
