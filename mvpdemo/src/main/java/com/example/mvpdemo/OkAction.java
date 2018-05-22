package com.example.mvpdemo;

import android.os.Message;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class OkAction {

    public static void get(String url, final ICallback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder;
        builder = new Request.Builder();
        Request request = builder.get().url(url).build();

        Call call  = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.i("msg", json);
                callback.seccess(json);

            }
        });
    }
}
