package com.bas.google_book_app.ui.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bas.google_book_app.R

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_book)
        val sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen = preferenceScreen
        val count = prefScreen.preferenceCount
        for (i in 0 until count) {
            val p = prefScreen.getPreference(i)
            val value = sharedPreferences.getString(p.key, DEFAULT_VALUE)
            setPreferenceSummary(p, value)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val preference = key?.let { findPreference<Preference?>(it) }
        if (null != preference) {
            val value = sharedPreferences?.getString(preference.key, DEFAULT_VALUE)
            setPreferenceSummary(preference, value)
        }
    }

    private fun setPreferenceSummary(preference: Preference?, value: String?) {
        if (preference is ListPreference) {
            val listPreference = preference as ListPreference?
            val prefIndex = listPreference?.findIndexOfValue(value)
            if (prefIndex != null) {
                if (prefIndex >= 0) {
                    listPreference?.setSummary(listPreference.getEntries()[prefIndex!!])
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        private val DEFAULT_VALUE: String? = ""
    }
}