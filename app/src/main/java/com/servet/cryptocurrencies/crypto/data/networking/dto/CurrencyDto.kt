package com.servet.cryptocurrencies.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: String,
    val priceUsd: String,
    val changePercent24Hr: Double
)
