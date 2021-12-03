package com.bas.google_book_app.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bas.google_book_app.MainActivity;
import com.bas.google_book_app.R;
import com.bas.google_book_app.databinding.ActivityLoginBinding;
import com.bas.google_book_app.ui.register.RegisterActivity;
import com.bas.google_book_app.utilsdata.UserSession;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mBinding.tbnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogin();
            }
        });

    }

    private void setLogin() {
        if (mBinding.edtUsername.getText().toString().isEmpty()){
            Toast.makeText(this, "Username can't Empty", Toast.LENGTH_SHORT).show();
        }else   if (mBinding.edtPass.getText().toString().isEmpty()){
            Toast.makeText(this, "Password can't Empty", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> getUserDetails =  new UserSession(this).getUserDetails();
            String username = getUserDetails.get("Name");
            String password = getUserDetails.get("txtPassword");
            if (mBinding.edtUsername.getText().toString().equals(username) &&
                    mBinding.edtPass.getText().toString().equals(password)){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                new UserSession(this).isUserLoggedIn();

            }else {
                Toast.makeText(this, "Username or Password is wrong", Toast.LENGTH_SHORT).show();

            }

        }
    }
}