package com.servet.cryptocurrencies.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyHistoryResponseDto(
    val data: List<CurrencyPriceDto>
)
