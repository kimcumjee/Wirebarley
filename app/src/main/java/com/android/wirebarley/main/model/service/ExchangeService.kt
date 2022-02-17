package com.android.wirebarley.main.model.service

import com.android.wirebarley.main.model.response.ExchangeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeService {
    @GET("live")
    suspend fun getBroadcastList(
        @Query("access_key") accessKey: String
    ): Response<ExchangeResponse>
}