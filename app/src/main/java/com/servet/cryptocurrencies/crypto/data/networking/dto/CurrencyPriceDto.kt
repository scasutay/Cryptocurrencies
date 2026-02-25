package com.servet.cryptocurrencies.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyPriceDto(
    val priceUsd: String,
    val time: Long
)
