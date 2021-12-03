package com.bas.google_book_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bas.google_book_app.MainActivity
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.ActivityLoginBinding
import com.bas.google_book_app.ui.login.LoginActivity
import com.bas.google_book_app.ui.register.RegisterActivity
import com.bas.google_book_app.utilsdata.UserSession

class LoginActivity : AppCompatActivity() {
    private var mBinding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding?.tvRegister?.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        mBinding?.tbnLogin?.setOnClickListener { setLogin() }
    }

    private fun setLogin() {
        if (mBinding?.edtUsername?.text.toString().isEmpty()) {
            Toast.makeText(this, "Username can't Empty", Toast.LENGTH_SHORT).show()
        } else if (mBinding?.edtPass?.text.toString().isEmpty()) {
            Toast.makeText(this, "Password can't Empty", Toast.LENGTH_SHORT).show()
        } else {
            val getUserDetails = UserSession(this)?.getUserDetails()
            val username = getUserDetails?.get("Name")
            val password = getUserDetails?.get("txtPassword")
            if (mBinding?.edtUsername?.text.toString() == username &&
                mBinding?.edtPass?.text.toString() == password) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                UserSession(this)?.isUserLoggedIn()
            } else {
                Toast.makeText(this, "Username or Password is wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}