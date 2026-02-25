package com.servet.cryptocurrencies.crypto.domain

import java.math.BigDecimal
import java.time.ZonedDateTime

data class CurrencyPrice(
    val priceUsd: BigDecimal,
    val dateTime: ZonedDateTime
)
