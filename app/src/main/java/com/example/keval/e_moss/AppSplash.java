package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mpt.storage.SharedPreferenceUtil;

public class AppSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        SharedPreferenceUtil.init(this);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_splash);
        startApp();
    }


    public void startApp() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    startActivity(new Intent(AppSplash.this, SignIn.class));
                    finish();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
}