package com.example.practiceHealth.utils

import android.content.Context
import org.json.JSONArray

class DataHandler  {

    companion object {


        private const val FILE_NAME_SHARED_PREF = "practiceStatus"


        /*fun updatePreferences(key: String, value: Long?) {
            val settings = context!!.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putLong(key, value!!)
            editor.apply()
        }*/

        fun updatePreferences(key: String, value: JSONArray, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(key, value.toString())
            editor.apply()

        }

        fun getLongReferences(key: String, context: Context): Long {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            return settings.getLong(key, 0)
        }

        fun updatePreferences(key: String, value: Boolean?, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putBoolean(key, value!!)
            editor.apply()

        }

        fun updatePreferences(key: String, value: Int, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(key, value)
            editor.apply()

        }

        fun updatePreferences(key: String, value: Double, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            editor.apply()

        }

        fun updatePreferences(key: String, value: String?, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(key, value)
            editor.apply()

        }

        fun getBooleanPreferences(key: String, context: Context): Boolean {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0)
            return settings.getBoolean(key, false)

        }

        fun deletePreference(key: String, context: Context) {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.remove(key)
            editor.apply()

        }

        fun getDoublePreference(key: String, context: Context): Double {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0)

            return java.lang.Double.longBitsToDouble(settings.getLong(key, java.lang.Double.doubleToLongBits(0.0)))
        }

        fun getIntPreferences(key: String, context: Context): Int {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0)
            return settings.getInt(key, -1)
        }

        // Preference for Passenger Mode
        fun getIntPreferencesPassengerMode(key: String, context: Context): Int {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, 0)
            return settings.getInt(key, 0)
        }

        fun getStringPreferences(key: String, context: Context): String {
            val settings = context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE)
            return settings.getString(key, "")!!

        }


        fun isContainKey(key: String, context: Context): Boolean {
            return context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE).contains(key)
        }

        fun clear(context: Context) {
            context.getSharedPreferences(FILE_NAME_SHARED_PREF, Context.MODE_PRIVATE).edit().clear().commit()
        }
    }


}