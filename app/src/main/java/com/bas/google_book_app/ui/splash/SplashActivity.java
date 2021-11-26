package com.bas.google_book_app.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bas.google_book_app.MainActivity;
import com.bas.google_book_app.R;
import com.bas.google_book_app.databinding.ActivitySplashBinding;
import com.bas.google_book_app.ui.login.LoginActivity;
import com.bas.google_book_app.utilsdata.UserSession;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                boolean login = new UserSession(SplashActivity.this).checkLogin();
                if (login){

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }else {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

            }
        }, 1000);
    }
}