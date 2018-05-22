package com.example.leak;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class BinderServiceStub extends Binder implements IBinderService {

    private final static String MSG = "I am from server";

    public BinderServiceStub() {
        attachInterface(this, DESCRIPITON);
    }

    @Override
    public String getMessage() {
        return MSG;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

        switch (code){
            case GET_MESSAGE:
                //先发送服务描述
                data.enforceInterface(DESCRIPITON);
                //开始执行客户端请求的服务端方法
                String msg = getMessage();
                //将结果写入Parcel
                reply.writeNoException();
                reply.writeString(msg);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
