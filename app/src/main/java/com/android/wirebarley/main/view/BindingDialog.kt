package com.android.wirebarley.main.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


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