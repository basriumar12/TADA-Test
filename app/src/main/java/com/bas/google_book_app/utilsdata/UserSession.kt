package com.bas.google_book_app.utilsdata

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.bas.google_book_app.ui.login.LoginActivity
import java.util.*

class UserSession(  // Context
    var _context: Context?
) {
    var pref: SharedPreferences?

    // Editor reference for Shared preferences
    var editor: SharedPreferences.Editor?

    // Shared preferences mode
    var PRIVATE_MODE = 0

    //Create login session
    fun createUserLoginSession(uName: String?, uPassword: String?) {
        // Storing login value as TRUE
        editor?.putBoolean(IS_USER_LOGIN, true)

        // Storing name in preferences
        editor?.putString(KEY_NAME, uName)

        // Storing email in preferences
        editor?.putString(KEY_PASSWORD, uPassword)

        // commit changes
        editor?.commit()
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    fun checkLogin(): Boolean {
        // Check login status
        if (!isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            val i = Intent(_context, LoginActivity::class.java)

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            _context?.startActivity(i)
            return true
        }
        return false
    }

    /**
     * Get stored session data
     */
    fun getUserDetails(): HashMap<String?, String?>? {

        //Use hashmap to store user credentials
        val user = HashMap<String?, String?>()

        // user name
        user[KEY_NAME] = pref?.getString(KEY_NAME, null)

        // user email id
        user[KEY_PASSWORD] = pref?.getString(KEY_PASSWORD, null)

        // return user
        return user
    }

    /**
     * Clear session details
     */
    fun logoutUser() {

        // Clearing all user data from Shared Preferences
        editor?.clear()
        editor?.commit()

        // After logout redirect user to MainActivity
        val i = Intent(_context, LoginActivity::class.java)

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // Staring Login Activity
        _context?.startActivity(i)
    }

    // Check for login
    fun isUserLoggedIn(): Boolean {
        return pref?.getBoolean(IS_USER_LOGIN, false) == true
    }

    companion object {
        // Shared preferences file name
        val PREFER_NAME: String? = "Reg"

        // All Shared Preferences Keys
        val IS_USER_LOGIN: String? = "IsUserLoggedIn"

        // User name (make variable public to access from outside)
        val KEY_NAME: String? = "Name"

        // Email address (make variable public to access from outside)
        val KEY_EMAIL: String? = "Email"

        // password
        val KEY_PASSWORD: String? = "txtPassword"
    }

    // Constructor
    init {
        pref = _context?.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref?.edit()
    }
}