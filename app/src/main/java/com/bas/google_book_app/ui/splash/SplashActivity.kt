package com.bas.google_book_app.ui.splash

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bas.google_book_app.MainActivity
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.ActivitySplashBinding
import com.bas.google_book_app.ui.login.LoginActivity
import com.bas.google_book_app.utilsdata.UserSession

class SplashActivity : AppCompatActivity() {
    private var mBinding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        Handler().postDelayed({
            val login = UserSession(this@SplashActivity).checkLogin()
            if (login) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        }, 1000)
    }
}