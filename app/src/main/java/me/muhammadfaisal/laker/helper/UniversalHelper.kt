package me.muhammadfaisal.laker.helper

import android.app.Activity
import android.view.WindowManager

class UniversalHelper {

    fun madeNoTouchable(yourActivity: Activity){
       yourActivity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun clearNoTouchable(yourActivity: Activity){
        yourActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}