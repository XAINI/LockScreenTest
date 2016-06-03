package com.mindpin.ben7th.lockscreentest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent service_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service_intent = new Intent(this, LockService.class);
    }

    public void enable_lock(View view) {
        Toast.makeText(getApplicationContext(), "启动锁屏监测服务", Toast.LENGTH_SHORT).show();
        Log.i(LockService.LOG_TAG, "启动锁屏监测服务");
        this.startService(service_intent);
    }

    public void disable_lock(View view) {
        Toast.makeText(getApplicationContext(), "停止锁屏监测服务", Toast.LENGTH_SHORT).show();
        Log.i(LockService.LOG_TAG, "停止锁屏监测服务");
        this.stopService(service_intent);
    }
}
