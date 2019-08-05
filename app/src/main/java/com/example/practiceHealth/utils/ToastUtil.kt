package com.example.practiceHealth.utils

import android.content.Context
import android.widget.Toast

class ToastUtil  {

    companion object {

        fun showShortToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        fun showShortToast(context: Context, msg: Int) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun showLongToast(context: Context, msg: Int) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }
}