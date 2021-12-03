package com.bas.google_book_app.db

import android.content.Context
import android.preference.PreferenceManager
import com.bas.google_book_app.R

object BookPreferences {
    fun getFilterPreference(context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val filterPrefKey = context?.getString(R.string.pref_key_filter_by)
        val filterDefault = context?.getString(R.string.pref_filter_by_default)
        return preferences.getString(filterPrefKey, filterDefault)
    }
}