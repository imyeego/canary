package com.example.leak;

import android.os.IBinder;
import android.os.IInterface;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public interface IBinderService extends IInterface {
    String DESCRIPITON = "MyBinderService";
    // 定义方法编号
    int GET_MESSAGE = IBinder.FIRST_CALL_TRANSACTION + 0;
    String getMessage();
}
