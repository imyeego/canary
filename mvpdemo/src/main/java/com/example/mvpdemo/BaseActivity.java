package com.example.mvpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView> extends AppCompatActivity {

    private P presenter;
    private V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) presenter = createPresenter();
        if (view == null) view = createView();
        if (presenter != null && view != null){
            presenter.attachView(view);
        }


    }

    public abstract P createPresenter();
    public abstract V createView();


    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null && view != null){
            presenter.detachView();
        }
    }
}
