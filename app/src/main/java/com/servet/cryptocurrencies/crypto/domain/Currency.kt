package com.servet.cryptocurrencies.crypto.domain

import java.math.BigDecimal

data class Currency(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: BigDecimal,
    val priceUsd: BigDecimal,
    val changePercent24Hr: Double
)
