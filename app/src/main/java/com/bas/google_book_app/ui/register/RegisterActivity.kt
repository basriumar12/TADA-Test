package com.bas.google_book_app.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.ActivityRegisterBinding
import com.bas.google_book_app.utilsdata.UserSession

class RegisterActivity : AppCompatActivity() {
    private var mBinding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        mBinding?.btnRegister?.setOnClickListener { setRegister() }
    }

    private fun setRegister() {
        val username = mBinding?.edtUsername?.text.toString()
        val password = mBinding?.edtPass?.text.toString()
        if (mBinding?.edtPass?.text.toString().isEmpty()) {
            Toast.makeText(this, "Password can't Empty", Toast.LENGTH_SHORT).show()
        } else if (mBinding?.edtUsername?.text.toString().isEmpty()) {
            Toast.makeText(this, "Username can't Empty", Toast.LENGTH_SHORT).show()
        } else {
            if (mBinding?.checkbox?.isChecked == true) {
                UserSession(this).createUserLoginSession(username, password)
                Toast.makeText(this, "Success create account", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Need check the term and condition", Toast.LENGTH_SHORT).show()
            }
        }
    }
}