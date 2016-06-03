package com.mindpin.ben7th.lockscreentest;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockService extends Service {
    public LockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        lock_intent = new Intent(LockService.this, LockActivity.class);
        lock_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        IntentFilter screen_on_filter = new IntentFilter("android.intent.action.SCREEN_ON");
        this.registerReceiver(screen_on_receiver, screen_on_filter);

        IntentFilter screen_off_filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        this.registerReceiver(screen_off_receiver, screen_off_filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(screen_on_receiver);
        this.unregisterReceiver(screen_off_receiver);
    }

    public final static String LOG_TAG = "LockService";

    private Intent lock_intent;

    private KeyguardManager key_guard_manager = null;
    private KeyguardManager.KeyguardLock key_guard_lock = null;

    private BroadcastReceiver screen_on_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {
                Log.i(LOG_TAG, "检测到屏幕开启动作: SCREEN_ON");
            }
        }
    };

    private BroadcastReceiver screen_off_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                Log.i(LOG_TAG, "检测到屏幕关闭动作 SCREEN_OFF");

                key_guard_manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                key_guard_lock = key_guard_manager.newKeyguardLock("");
                key_guard_lock.disableKeyguard();

                context.startActivity(lock_intent);
            }
        }
    };
}
