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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import greendao.Account;
import greendao.AccountDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by Administrator on 2017/12/31 0031.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Account> mlist;

    private Context mContext;

    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private AccountDao accountDao;


    public RecyclerAdapter(Context context, List<Account> list) {
        this.mlist = list;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        db = new DaoMaster.DevOpenHelper(context, "account", null).getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        accountDao = mDaoSession.getAccountDao();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, null);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Account bean = mlist.get(position);
        final MyViewHolder holder = viewHolder;
        holder.bindHolder(bean);

        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                View menuView = layoutInflater.inflate(R.layout.menu_view, null);
                View rootView = holder.view.getRootView();
                final Window window = ((Activity)mContext).getWindow();

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(menuView, width, height);
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

                TextView delete = (TextView) menuView.findViewById(R.id.item_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mlist.remove(bean);
                        RecyclerAdapter.this.notifyItemRemoved(position);
                        accountDao.delete(bean);
                        popupWindow.dismiss();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
