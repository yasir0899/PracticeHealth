package com.example.practiceHealth.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class HideSoftKeyBoard {
    companion object {
        fun closeKeyboard(activity: Activity) {
            val view: View? = activity.currentFocus
            if (view != null) {
                (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    view.windowToken,
                    0
                )
            }
        }

    }
}