package com.android.wirebarley.main.model.response

import com.android.wirebarley.main.model.response.Quotes

data class ExchangeResponse(
    val privacy: String,
    val quotes: Quotes,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Int
)