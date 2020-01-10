package me.muhammadfaisal.laker.helper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import me.muhammadfaisal.laker.R

class Loading(context: Context?) : AlertDialog(context) {



    override fun show() {
        super.show()
        setContentView(R.layout.loading_view)
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
    }

    override fun cancel() {
        super.cancel()
    }

}