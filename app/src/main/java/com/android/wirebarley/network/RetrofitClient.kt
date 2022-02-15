package com.android.wirebarley.network


import com.android.wirebarley.main.model.service.ExchangeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val retrofitBuild: Retrofit = Retrofit.Builder()
        .baseUrl("http://api.currencylayer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val getExchangeList: ExchangeService =
        retrofitBuild.create(ExchangeService::class.java)
    companion object {
        val instance = RetrofitClient()
    }
}