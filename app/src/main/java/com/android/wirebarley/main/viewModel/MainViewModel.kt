package com.android.wirebarley.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.wirebarley.BuildConfig
import com.android.wirebarley.main.model.response.ExchangeResponse
import com.android.wirebarley.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val io = Dispatchers.IO
    private val main = Dispatchers.Main
    val KRWReceived = MutableLiveData<Double>()
    val JPYReceived = MutableLiveData<Double>()
    val PHPReceived = MutableLiveData<Double>()
    fun getKRW() {
        CoroutineScope(io).launch {
            val getExchangeApi = RetrofitClient.instance.getExchangeList
            val response = getExchangeApi.getBroadcastList(BuildConfig.EXCHANGE_API_KEY)
            if (response.isSuccessful) {
                withContext(main) {
                    KRWReceived.value = response.body()?.quotes?.USDKRW
                }
            }
        }
    }
    fun getJPY() {
        CoroutineScope(io).launch {
            val getExchangeApi = RetrofitClient.instance.getExchangeList
            val response = getExchangeApi.getBroadcastList(BuildConfig.EXCHANGE_API_KEY)
            if (response.isSuccessful) {
                withContext(main) {
                    JPYReceived.value = response.body()?.quotes?.USDJPY
                }
            }
        }
    }
    fun getPHP() {
        CoroutineScope(io).launch {
            val getExchangeApi = RetrofitClient.instance.getExchangeList
            val response = getExchangeApi.getBroadcastList(BuildConfig.EXCHANGE_API_KEY)
            if (response.isSuccessful) {
                withContext(main) {
                    PHPReceived.value = response.body()?.quotes?.USDPHP
                }
            }
        }
    }
}