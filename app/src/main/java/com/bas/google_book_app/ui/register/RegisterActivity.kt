package com.bas.google_book_app.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bas.google_book_app.R;
import com.bas.google_book_app.databinding.ActivityRegisterBinding;
import com.bas.google_book_app.utilsdata.UserSession;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setRegister();
            }
        });
    }

    private void setRegister() {
        String username = mBinding.edtUsername.getText().toString();
        String password = mBinding.edtPass.getText().toString();
        if (mBinding.edtPass.getText().toString().isEmpty()){
            Toast.makeText(this, "Password can't Empty", Toast.LENGTH_SHORT).show();
        }else if(mBinding.edtUsername.getText().toString().isEmpty()){
            Toast.makeText(this, "Username can't Empty", Toast.LENGTH_SHORT).show();
        }else {

            if (mBinding.checkbox.isChecked()){
                new UserSession(this).createUserLoginSession(username,password);
                Toast.makeText(this, "Success create account", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "Need check the term and condition", Toast.LENGTH_SHORT).show();
            }

        }
    }
}