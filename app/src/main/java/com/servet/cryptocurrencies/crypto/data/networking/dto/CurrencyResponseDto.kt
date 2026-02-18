package com.servet.cryptocurrencies.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponseDto(
    val data: List<CurrencyDto>
)
