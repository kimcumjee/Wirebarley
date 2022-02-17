package com.android.wirebarley.main.view

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.android.wirebarley.R
import com.android.wirebarley.databinding.DialogSetCountryBinding
import com.android.wirebarley.main.viewModel.MainViewModel

class Dialog(private val activity: Activity, private val viewModel: MainViewModel) {
    fun changeReceiveCountryDialog() : AlertDialog {
        val binding= BindingDialog<DialogSetCountryBinding>(
            activity,
            R.layout.dialog_set_country
        )
        binding.binding.apply {
            btnSetJPY.setOnClickListener {

            }
            btnSetKRW.setOnClickListener {

            }
            btnSetPHP.setOnClickListener {

            }
        }
        return binding.getAlertDialog()
    }
}