package com.example.administrator.accountbook;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import greendao.Account;
import greendao.AccountDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Administrator on 2017/12/31 0031.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle;
    public TextView mMoney;
    public TextView mTime;
    public ImageButton mImageButton;
    public final View view;
    private Context mContext;
    private LayoutInflater mLayoutInflater;



    public MyViewHolder(View itemView, Context context) {
        super(itemView);
        this.view = itemView;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        mTitle = (TextView) itemView.findViewById(R.id.id_title);
        mMoney = (TextView) itemView.findViewById(R.id.id_money);
        mTime = (TextView) itemView.findViewById(R.id.id_time);
        mImageButton = (ImageButton) itemView.findViewById(R.id.id_menu);



    }

    public void bindHolder(final Account bean){
        mTitle.setText(bean.getTitle());
        mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mTitle.setSingleLine(true);
        mTitle.setSelected(true);
        mTitle.setFocusable(true);
        mTitle.setFocusableInTouchMode(true);

        mMoney.setText("¥" + bean.getMoney());
        mTime.setText(bean.getTime());


    }
}
