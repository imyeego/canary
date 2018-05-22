package com.example.administrator.accountbook;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import greendao.Account;
import greendao.AccountDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private int mHeight;
    private List<Account> mList;
    private LayoutInflater mLayoutInflater;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private AccountDao accountDao;

    public MyAdapter(Context context, List<Account> list, int height) {

        this.mContext = context;
        this.mList = list;
        this.mHeight = height;
        mLayoutInflater = LayoutInflater.from(context);
        db = new DaoMaster.DevOpenHelper(context, "account", null).getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        accountDao = mDaoSession.getAccountDao();

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
            viewHolder.mMoney = (TextView) convertView.findViewById(R.id.id_money);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.id_time);
            viewHolder.mImageButton = (ImageButton) convertView.findViewById(R.id.id_menu);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        final Account bean = mList.get(position);
        viewHolder.mTitle.setText(bean.getTitle());
        viewHolder.mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        viewHolder.mTitle.setSingleLine(true);
        viewHolder.mTitle.setSelected(true);
        viewHolder.mTitle.setFocusable(true);
        viewHolder.mTitle.setFocusableInTouchMode(true);

        viewHolder.mMoney.setText("¥" + bean.getMoney());
        viewHolder.mTime.setText(bean.getTime());

        final View finalConvertView = convertView;
        viewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                View view = mLayoutInflater.inflate(R.layout.menu_view, null);
                View rootView = finalConvertView.getRootView();
                final Window window = ((Activity)mContext).getWindow();
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(view, width, height);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(R.style.Animation);
                popupWindow.setElevation(5);
                popupWindow.update();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 0.7f;
                window.setAttributes(lp);
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = window.getAttributes();
                        lp.alpha = 1.0f;
                        window.setAttributes(lp);
                    }
                });

                TextView delete = (TextView) view.findViewById(R.id.item_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.remove(position);
                        MyAdapter.this.notifyDataSetChanged();
                        accountDao.delete(bean);
                        popupWindow.dismiss();

                    }
                });
            }
        });



        return convertView;
    }

    public static class ViewHolder{

        public TextView mTitle;
        public TextView mMoney;
        public TextView mTime;
        public ImageButton mImageButton;

    }


}
