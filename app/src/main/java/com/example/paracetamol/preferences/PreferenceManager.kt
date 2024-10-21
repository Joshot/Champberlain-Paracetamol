package com.example.paracetamol.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Utility class for managing shared preferences related to user sessions and tokens.
 */
object PreferenceManager {
    private const val PREF_NAME = "user_prefs"
    private const val KEY_MEMBER_ID = "member_id"
    private const val KEY_USER_TOKEN = "user_token"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    /**
     * Save the member ID in shared preferences.
     */
    fun saveMemberID(context: Context, memberID: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_MEMBER_ID, memberID).apply()
    }

    /**
     * Retrieve the member ID from shared preferences.
     */
    fun getMemberID(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(KEY_MEMBER_ID, null)
    }

    /**
     * Save the login status in shared preferences.
     */
    fun saveIsLoggedIn(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
    }

    /**
     * Retrieve the login status from shared preferences.
     */
    fun getIsLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * Save the user token in shared preferences.
     */
    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_USER_TOKEN, token).apply()
    }

    /**
     * Retrieve the user token from shared preferences.
     */
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }

    /**
     * Clear all preferences stored in shared preferences.
     */
    fun clearPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}

