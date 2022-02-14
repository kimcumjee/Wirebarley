package com.android.wirebarley.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val retrofitBuild: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.shoplive.cloud/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        val instance = RetrofitClient()
    }
}