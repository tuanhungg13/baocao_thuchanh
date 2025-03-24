package com.dev.oursharedpreference.helper

import android.content.SharedPreferences
class PreferenceHelper(private val sharedPreferences: SharedPreferences) {
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveData(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
    fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}