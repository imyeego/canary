package com.example.administrator.accountbook;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.id_search_view)
    SearchView mSearchView;

    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_tv)
    TextView mTextView;

    private SearchView.SearchAutoComplete searchAutoComplete;

    private MessageBroadcastReceiver receiver;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_acti);

        ButterKnife.bind(this);
        searchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);

        mSearchView.setIconified(true);
        Drawable drawable = getResources().getDrawable(R.drawable.round_corner);
        searchAutoComplete.setBackground(drawable);
        searchAutoComplete.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                ((InputMethodManager)searchAutoComplete.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS
                );

                String query = searchAutoComplete.getText().toString();

                SessionManager.getInstance().writeToServer(query);
                Log.d("start Mina", query);
            }



            return true;
        });

        mSearchView.onActionViewExpanded();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        registerBroadcast();

        Intent intent = new Intent(SearchActivity.this, MinaService.class);
        startService(intent);
    }

    private void registerBroadcast() {

        receiver = new MessageBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.administrator.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.menu_search);
//        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MinaService.class));
        unregisterBroadcast();
    }

    private void unregisterBroadcast() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public class MessageBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            mTextView.setText(message);
        }
    }
}
