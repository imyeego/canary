package com.example.administrator.accountbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

import greendao.AccountDao;
import greendao.DaoMaster;
import greendao.DaoSession;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter myAdapter;
    private List<greendao.Account> itemBeanList;
    private FloatingActionButton fab;
    private TextView mTextView;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private AccountDao accountDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.id_fab);
        mTextView = (TextView) findViewById(R.id.tv_blank);
        recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        openDb();
        initDatas();
        myAdapter = new RecyclerAdapter(this, itemBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

//        MyAdapter.ViewHolder viewHolder
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));


        fab.setRippleColor(Color.BLACK);

        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.add_view, null);
            final EditText title = (EditText) view.findViewById(R.id.ed_title);
            final EditText money = (EditText) view.findViewById(R.id.ed_money);

            builder.setView(view);
            builder.setTitle("add a account");
            builder.setPositiveButton("OK", (dialog, which) -> {
                if (recyclerView.getVisibility() == View.GONE && mTextView.getVisibility() == View.VISIBLE){
                    recyclerView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.GONE);
                }
                greendao.Account bean = new greendao.Account();
                bean.setTitle(title.getText().toString());
                bean.setMoney(money.getText().toString());
                bean.setTime(TimeTool.currentTime("MM-dd HH:mm"));
                accountDao.insert(bean);
                itemBeanList.add(0,bean);
                myAdapter.notifyItemInserted(0);
            });
            builder.setNegativeButton("CANCEL", null);
            builder.create().show();
        });


    }


    void initDatas(){

        itemBeanList = accountDao.queryBuilder().orderAsc(AccountDao.Properties.Time).list();
        if (itemBeanList.size() != 0){
            recyclerView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.id_search_button:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDb(){
        db = new DaoMaster.DevOpenHelper(this, "account", null).getWritableDatabase();

        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        accountDao = mDaoSession.getAccountDao();

    }
}
