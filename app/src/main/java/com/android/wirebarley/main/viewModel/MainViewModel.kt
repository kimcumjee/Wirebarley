package com.android.wirebarley.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.wirebarley.BuildConfig
import com.android.wirebarley.main.model.response.ExchangeResponse
import com.android.wirebarley.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val io = Dispatchers.IO
    private val main = Dispatchers.Main
    val exchangeReceived = MutableLiveData<ExchangeResponse>()
    fun getExchangeList() {
        CoroutineScope(io).launch {
            val getExchangeApi = RetrofitClient.instance.getExchangeList
            val response = getExchangeApi.getBroadcastList(BuildConfig.EXCHANGE_API_KEY)
            if (response.isSuccessful) {
                exchangeReceived.value = response.body()
            } else {

            }
        }
    }
}