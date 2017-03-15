package com.example.slidlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private LockView mLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLockView = (LockView) findViewById(R.id.lock_view);
        mLockView.setOnUnlockListener(new LockView.OnUnlockListener() {
            @Override
            public void unLock() {
                finish();
            }
        });
    }
}
