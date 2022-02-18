package com.android.wirebarley.main.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
    private val koreaCode = 0
    private val japanCode = 1
    private val philippines = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val dialog = Dialog(this, viewModel)
        val changeReceiveCountryDialog: AlertDialog = dialog.changeReceiveCountryDialog()
        viewModel.getExchangeRate(this, koreaCode)
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
                        koreaCode
                    )
                    this@MainActivity.getString(R.string.수취국가_일본) -> viewModel.getExchangeRate(
                        this@MainActivity,
                        japanCode
                    )
                    this@MainActivity.getString(R.string.수취국가_필리핀) -> viewModel.getExchangeRate(
                        this@MainActivity,
                        philippines
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
                koreaCode -> {
                    viewModel.getExchangeRate(this, koreaCode)
                    changeReceiveCountryDialog.dismiss()
                }
                japanCode -> {
                    viewModel.getExchangeRate(this, japanCode)
                    changeReceiveCountryDialog.dismiss()
                }
                philippines -> {
                    viewModel.getExchangeRate(this, philippines)
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
        val result = dec.format(bdExchangeRate * bdSendMoney)
        binding.run {
            when (countryCode) {
                0 -> binding.tvResultReceiveMoney.text = result.toString()
                1 -> binding.tvResultReceiveMoney.text = result.toString()
                2 -> binding.tvResultReceiveMoney.text = result.toString()
            }
        }
    }
}
//when (tvReceiveCountry.text) {
//    this@MainActivity.getText(R.string.한국_KRW) -> viewModel.getExchangeRate(
//    this@MainActivity,
//    koreaCode
//    )
//    this@MainActivity.getText(R.string.일본_JPY) -> viewModel.getExchangeRate(
//    this@MainActivity,
//    japanCode
//    )
//    this@MainActivity.getText(R.string.필리핀_PHP) -> viewModel.getExchangeRate(
//    this@MainActivity,
//    philippines
//    )
//}