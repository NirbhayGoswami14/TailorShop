package com.app.tailorusershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.app.tailorusershop.R;
import com.app.tailorusershop.utlis.PrefManager;

public class SplashActivity extends AppCompatActivity {

    private Context context=SplashActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Log.d("", "run:"+PrefManager.getInstance(context).isLogin());
               startActivity(new Intent(context,HomeActivity.class));*/
                if(PrefManager.getInstance(context).isLogin())
                {
                    startActivity(new Intent(context,HomeActivity.class));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        },2000);
    }
}