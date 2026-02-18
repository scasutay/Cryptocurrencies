package com.servet.cryptocurrencies.crypto.data.mapper

import com.servet.cryptocurrencies.core.domain.extensions.toBigDecimalSafe
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyDto
import com.servet.cryptocurrencies.crypto.domain.Currency

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        id = id,
        rank =rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toBigDecimalSafe(),
        priceUsd = priceUsd.toBigDecimalSafe(),
        changePercent24Hr = changePercent24Hr
    )
}