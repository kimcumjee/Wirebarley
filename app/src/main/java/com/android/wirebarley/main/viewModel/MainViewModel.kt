package com.android.wirebarley.main.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.wirebarley.BuildConfig
import com.android.wirebarley.R
import com.android.wirebarley.`object`.CountryCode
import com.android.wirebarley.main.model.Country
import com.android.wirebarley.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val io = Dispatchers.IO
    private val main = Dispatchers.Main
    val received = MutableLiveData<Country>()
    val changeCountry = MutableLiveData<Int>()
    fun getExchangeRate(context: Context, countryCode: Int) {
        CoroutineScope(io).launch {
            val getExchangeApi = RetrofitClient.instance.getExchangeList
            val response = getExchangeApi.getBroadcastList(BuildConfig.EXCHANGE_API_KEY)
            if (response.isSuccessful) {
                withContext(main) {
                    if (response.body()?.quotes?.USDKRW == null) {
                        Toast.makeText(context, context.getText(R.string.api_error_msg), Toast.LENGTH_SHORT).show()
                    } else {
                        when (countryCode) {
                            CountryCode.korea -> received.value =
                                Country(response.body()!!.quotes.USDKRW, countryCode)
                            CountryCode.japan -> received.value =
                                Country(response.body()!!.quotes.USDJPY, countryCode)
                            CountryCode.philippines -> received.value =
                                Country(response.body()!!.quotes.USDPHP, countryCode)
                        }
                    }
                }
            }
        }
    }
}