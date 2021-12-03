package com.bas.google_book_app.ui.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.ActivityProfilBinding
import com.bas.google_book_app.ui.profil.ProfilActivity
import com.bas.google_book_app.utilsdata.UserSession

class ProfilActivity : AppCompatActivity() {
    private var mBinding: ActivityProfilBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profil)
        val getUserDetails = UserSession(this).getUserDetails()
        val username = getUserDetails?.get("Name")
        mBinding?.tvUser?.text = username
        mBinding?.tvLogout?.setOnClickListener {
            UserSession(this@ProfilActivity).logoutUser()
            finish()
        }
    }
}