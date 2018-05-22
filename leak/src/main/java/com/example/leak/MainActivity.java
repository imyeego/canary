package com.example.leak;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, MyService.class);
        bindService(i, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service.queryLocalInterface(IBinderService.DESCRIPITON) == null){
                    BinderServiceProxy proxy = new BinderServiceProxy(service);
                    Log.i("msg", "" + proxy.getMessage());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);


        btn = findViewById(R.id.btn_next);
        btn.setOnClickListener(v->{
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        });
    }
}
