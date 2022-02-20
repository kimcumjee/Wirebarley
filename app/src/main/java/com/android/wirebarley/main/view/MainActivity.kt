package com.android.wirebarley.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.wirebarley.R
import com.android.wirebarley.`object`.CountryCode
import com.android.wirebarley.databinding.ActivityMainBinding
import com.android.wirebarley.main.view.dialog.Dialog
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
        viewModel.getExchangeRate(this, CountryCode.korea)
        binding.apply {
            etSendMoney.addTextChangedListener {
                if (it.isNullOrBlank()) {
                    this.tvResultReceiveMoney.text = ""
                }
            }
            btnChangeReceiveCountry.setOnClickListener {
                changeReceiveCountryDialog.show()
            }
            btnCalculateReceiveMoney.setOnClickListener {
                when (tvReceiveCountry.text) {
                    this@MainActivity.getString(R.string.수취국가_한국) -> viewModel.getExchangeRate(
                        this@MainActivity,
                        CountryCode.korea
                    )
                    this@MainActivity.getString(R.string.수취국가_일본) -> viewModel.getExchangeRate(
                        this@MainActivity,
                        CountryCode.japan
                    )
                    this@MainActivity.getString(R.string.수취국가_필리핀) -> viewModel.getExchangeRate(
                        this@MainActivity,
                        CountryCode.philippines
                    )
                }
            }
        }

        viewModel.received.observe(this) {
            getTime()
            setReceivedCountry(it.exchangeRate, it.countryCode)
            if (binding.etSendMoney.text!!.isNotBlank()) {
                calculateReceivedMoney(
                    binding.etSendMoney.text.toString(),
                    it.exchangeRate,
                    it.countryCode
                )
            }
        }

        viewModel.changeCountry.observe(this) {
            when (it) {
                CountryCode.korea -> {
                    viewModel.getExchangeRate(this, CountryCode.korea)
                    changeReceiveCountryDialog.dismiss()
                }
                CountryCode.japan -> {
                    viewModel.getExchangeRate(this, CountryCode.japan)
                    changeReceiveCountryDialog.dismiss()
                }
                CountryCode.philippines -> {
                    viewModel.getExchangeRate(this, CountryCode.philippines)
                    changeReceiveCountryDialog.dismiss()
                }
            }
        }
    }

    private fun getTime() {
        val now = System.currentTimeMillis()
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val date = formatter.format(now)
        val text = getText(R.string.조회시간).toString() + date
        binding.tvTime.text = text
    }

    @SuppressLint("SetTextI18n")
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

    @SuppressLint("SetTextI18n")
    private fun calculateReceivedMoney(sendMoney: String, exchangeRate: Double, countryCode: Int) {
        val bdSendMoney = BigDecimal(sendMoney)
        val bdExchangeRate = BigDecimal(exchangeRate)
        val result = dec.format(bdExchangeRate * bdSendMoney)
        binding.run {
            when (countryCode) {
                CountryCode.korea -> binding.tvResultReceiveMoney.text = "수취금액은 $result KRW 입니다."
                CountryCode.japan -> binding.tvResultReceiveMoney.text = "수취금액은 $result JPY 입니다."
                CountryCode.philippines -> binding.tvResultReceiveMoney.text =
                    "수취금액은 $result PHP 입니다."
            }
        }
    }
}