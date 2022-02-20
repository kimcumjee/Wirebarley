package com.android.wirebarley.main.view.dialog

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
            btnSetKRW.setOnClickListener {
                viewModel.changeCountry.value = 0
            }
            btnSetJPY.setOnClickListener {
                viewModel.changeCountry.value = 1
            }
            btnSetPHP.setOnClickListener {
                viewModel.changeCountry.value = 2
            }
        }
        return binding.getAlertDialog()
    }
}