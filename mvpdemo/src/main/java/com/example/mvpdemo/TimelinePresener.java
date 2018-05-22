package com.example.mvpdemo;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class TimelinePresener extends BasePresenter<TimelineView> {

    private TimelineModel timelineModel;

    public TimelinePresener(){
        this.timelineModel = new TimelineModel();
    }



    public void getTimeline(){
        timelineModel.getTimeline(new ICallback() {
            @Override
            public void seccess(String response) {
                getView().onTimelineResult(response);
            }
        });
    }
}
