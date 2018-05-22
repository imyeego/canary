package com.example.administrator.accountbook;

import org.apache.mina.core.session.IoSession;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class SessionManager {

    private static SessionManager mInstance = null;

    private IoSession mSession;

    public static SessionManager getInstance(){
        if (mInstance == null){
            synchronized (SessionManager.class){
                if (mInstance == null)
                    mInstance = new SessionManager();
            }

        }
        return mInstance;
    }

    public SessionManager(){

    }

    public void setmSession(IoSession mSession) {
        this.mSession = mSession;
    }

    public void writeToServer(Object msg){

        if (mSession != null){
            mSession.write(msg);
        }
    }

    public void closeSession(){
        if (mSession != null){
            mSession.close(true);
        }
    }

    public void removeSession(){
        this.mSession = null;
    }
}
