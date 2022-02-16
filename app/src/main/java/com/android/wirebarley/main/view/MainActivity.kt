package com.android.wirebarley.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.wirebarley.R
import com.android.wirebarley.databinding.ActivityMainBinding
import com.android.wirebarley.main.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getExchangeList()
        getTime()
        viewModel.exchangeReceived.observe(this) {
            
        }
    }
    private fun getTime() {
        val now = System.currentTimeMillis()
        val formatter = SimpleDateFormat("yyyy-MM-dd hh::mm", Locale.getDefault())
        val date = formatter.format(now)
        val text = getText(R.string.조회시간).toString() + date
        binding.tvTime.text = text
    }
}