package com.android.wirebarley.main.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.wirebarley.R
import com.android.wirebarley.databinding.ActivityMainBinding
import com.android.wirebarley.main.viewModel.MainViewModel
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel = MainViewModel()
    private val dec = DecimalFormat("#,###.00")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val dialog = Dialog(this, viewModel)
        val changeReceiveCountryDialog: AlertDialog = dialog.changeReceiveCountryDialog()
        var sendMoney = "0"
        viewModel.getKRW()
        binding.apply {
            btnChangeReceiveCountry.setOnClickListener {
                changeReceiveCountryDialog.show()
            }
            btnCalculateReceiveMoney.setOnClickListener {
                sendMoney = etSendMoney.text.toString()
                when (tvReceiveCountry.text) {
                    this@MainActivity.getText(R.string.한국_KRW) -> viewModel.getKRW()
                    this@MainActivity.getText(R.string.일본_JPY) -> viewModel.getJPY()
                    this@MainActivity.getText(R.string.필리핀_PHP) -> viewModel.getPHP()
                }
            }
        }
        viewModel.KRWReceived.observe(this) {
            getTime()
            setReceivedCountry(it, 0)
            calculateReceivedMoney(sendMoney, it, 0)
        }
        viewModel.JPYReceived.observe(this) {
            getTime()
            setReceivedCountry(it, 1)
            calculateReceivedMoney(sendMoney, it, 1)
        }
        viewModel.PHPReceived.observe(this) {
            getTime()
            setReceivedCountry(it, 2)
            calculateReceivedMoney(sendMoney, it, 2)
        }
    }

    private fun getTime() {
        val now = System.currentTimeMillis()
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val date = formatter.format(now)
        val text = getText(R.string.조회시간).toString() + date
        binding.tvTime.text = text
    }

    private fun setReceivedCountry(exchangeRate: Double, countryCode: Int) {

        val korea = getString(R.string.수취국가_한국)
        val japan = getString(R.string.수취국가_일본)
        val philippines = getString(R.string.수취국가_필리핀)
        binding.run {
            when (countryCode) {
                0 -> {
                    tvReceiveCountry.text = korea
                    tvExchangeRate.text = "환율:${dec.format(exchangeRate)} KRW/USD"
                }
                1 -> {
                    tvReceiveCountry.text = japan
                    tvExchangeRate.text = "환율:${dec.format(exchangeRate)} JPY/USD"
                }
                2 -> {
                    tvReceiveCountry.text = philippines
                    tvExchangeRate.text = "환율:${dec.format(exchangeRate)} PHP/USD"
                }
            }
        }
    }

    private fun calculateReceivedMoney(sendMoney: String, exchangeRate: Double, countryCode: Int) {
        val bdSendMoney = BigDecimal(sendMoney)
        val bdExchangeRate = BigDecimal(exchangeRate)
        val result = bdExchangeRate * bdSendMoney
        binding.run {
            when (countryCode) {
                0 -> binding.tvResultReceiveMoney.text = result.toString()
                1 -> binding.tvResultReceiveMoney.text = result.toString()
                2 -> binding.tvResultReceiveMoney.text = result.toString()
            }
        }
    }
}