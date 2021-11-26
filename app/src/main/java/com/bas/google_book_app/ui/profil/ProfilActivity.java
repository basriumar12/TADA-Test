package com.bas.google_book_app.ui.profil;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bas.google_book_app.R;
import com.bas.google_book_app.databinding.ActivityProfilBinding;
import com.bas.google_book_app.utilsdata.UserSession;

import java.util.HashMap;


public class ProfilActivity extends AppCompatActivity {

    private ActivityProfilBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profil);
        HashMap<String, String> getUserDetails =  new UserSession(this).getUserDetails();
        String username = getUserDetails.get("Name");

        mBinding.tvUser.setText(username);

        mBinding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserSession(ProfilActivity.this).logoutUser();
                finish();
            }
        });


    }
}