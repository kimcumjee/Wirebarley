package com.android.wirebarley.main.view.dialog

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


class BindingDialog<T : ViewDataBinding>(private val activity: Activity, layoutRes: Int) {

    val binding: T = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutRes, null, false)

    fun getAlertDialog(rejectBackgroundTouchDismiss: Boolean = false): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(binding.root)
        if (rejectBackgroundTouchDismiss) {
            dialogBuilder.setCancelable(false)
        }
        return dialogBuilder.create()
    }
}