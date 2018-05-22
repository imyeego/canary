package com.example.scrollingbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/4 0004.
 */

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.id_imageview)
    ImageView imageView;

    @BindView(R.id.id_download_btn)
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_load);



    }
}
