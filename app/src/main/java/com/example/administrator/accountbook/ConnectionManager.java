package com.example.administrator.accountbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class ConnectionManager {

    public static String BROADCAST_ACTION = "com.example.administrator.broadcast";
    public static String MESSAGE = "message";

    private ConnectionConfig config;
    private WeakReference<Context> mContext;
    private IoConnector mConnector;
    private IoSession mSession;
    private InetSocketAddress mAddress;

    public ConnectionManager(ConnectionConfig config) {
        this.config = config;

        this.mContext = new WeakReference<Context>(config.getContext());
        init();
    }

    private void init(){
        mAddress = new InetSocketAddress(config.getIp(), config.getPort());
        mConnector = new NioSocketConnector(1);
        mConnector.getSessionConfig().setReadBufferSize(config.getReadBufferSize());
        mConnector.getFilterChain().addLast("Logger:", new LoggingFilter());
        mConnector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, (int) config.getConnectionTimeOut());
//        mConnector.setDefaultRemoteAddress(mAddress);

        mConnector.setHandler(new MinaHandler(mContext.get()));
    }

    public boolean connect(){
        try {
            ConnectFuture future = mConnector.connect(mAddress);
            future.awaitUninterruptibly();
            mSession = future.getSession();
            SessionManager.getInstance().setmSession(mSession);
        } catch (Exception e) {
            Log.d("connect:", "false");
            return false;
        }

        return mSession == null ? false : true;
    }

    public void disconnect(){
        mConnector.dispose();
        mConnector = null;
        mSession = null;
        mAddress = null;
        mContext = null;
    }

    private static class MinaHandler extends IoHandlerAdapter{
        private Context mContext;

        public MinaHandler(Context context){
            this.mContext = context;

        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);

            if (mContext != null){
                Intent intent = new Intent(BROADCAST_ACTION);
                intent.putExtra(MESSAGE, message.toString());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        }
    }
}
