package com.example.alldocviewer.util

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat

object Extension {

    //todo set status bar color
    fun Activity.setStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)

            //todo Check if the color is light (like white) and set the light status bar flag
            if (isColorLight(ContextCompat.getColor(this, colorResId))) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //todo Clear the light status bar flag if the color is dark
                window.decorView.systemUiVisibility = 0
            }
        }
    }

    //todo Helper function to determine if a color is light
    private fun isColorLight(color: Int): Boolean {
        val darkness = 1 - (0.299 * ((color shr 16) and 0xFF) +
                0.587 * ((color shr 8) and 0xFF) +
                0.114 * (color and 0xFF)) / 255
        return darkness < 0.5
    }

}