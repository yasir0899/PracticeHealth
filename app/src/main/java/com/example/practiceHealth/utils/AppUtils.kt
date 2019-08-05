package com.example.practiceHealth.utils

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.regex.Pattern

class AppUtils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            val emailPattern =
                "(?:[a-zA-Z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            val pattern = Pattern.compile(emailPattern)
            return !TextUtils.isEmpty(email) && pattern.matcher(email).matches()
        }

        fun isValidUserName(username: String): Boolean {
            val userNamePattern =
                "^[a-zA-Z0-9._-]{3,}\$"
            val pattern = Pattern.compile(userNamePattern)
            return !TextUtils.isEmpty(username) && pattern.matcher(username).matches()
        }

        fun requestFocus(activity: Activity, view: View) {
            if (view.requestFocus()) {
                activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }


        fun focusOut(c: ConstraintLayout, activity: Activity) {

            c.isFocusableInTouchMode = true
            c.requestFocus()
            c.requestFocusFromTouch()
            c.isFocusable = true
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(c.windowToken, 0)

            Log.i("focusOut", " fun called")


        }
    }
}