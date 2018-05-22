package com.example.administrator.accountbook;

import android.content.Context;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class ConnectionConfig {

    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeOut;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public static class Builder{

        private Context context;
        private String ip;
        private int port;
        private int readBufferSize;
        private long connectionTimeOut;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;

        }

        public Builder setReadBufferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;

        }

        public Builder setConnectionTimeOut(long connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;

        }

        private void applyConfig(ConnectionConfig config){
            config.context = this.context;
            config.ip = this.ip;
            config.port = this.port;
            config.readBufferSize = this.readBufferSize;
            config.connectionTimeOut = this.connectionTimeOut;

        }

        public ConnectionConfig builder(){
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }
    }
}
