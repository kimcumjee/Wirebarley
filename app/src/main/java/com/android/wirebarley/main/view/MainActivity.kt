package com.android.wirebarley.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.wirebarley.databinding.ActivityMainBinding
import com.android.wirebarley.main.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getExchangeList()
        viewModel.exchangeReceived.observe(this) {
            
        }
    }
}