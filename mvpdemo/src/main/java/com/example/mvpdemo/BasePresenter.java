package com.example.mvpdemo;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public abstract class BasePresenter<V extends BaseView> {

    private V timelineView;

    public V getView() {
        return timelineView;
    }

    public void attachView(V view){
        this.timelineView = view;

    }

    public void detachView(){
        timelineView = null;
    }
}
