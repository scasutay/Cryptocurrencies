package com.servet.cryptocurrencies.crypto.data.mapper

import com.servet.cryptocurrencies.core.domain.extensions.toBigDecimalSafe
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyDto
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyPriceDto
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.crypto.domain.CurrencyPrice
import java.time.Instant
import java.time.ZoneId

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

fun CurrencyPriceDto.toCurrencyPrice(): CurrencyPrice {
    return CurrencyPrice(
        priceUsd = priceUsd.toBigDecimalSafe(),
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.of("UTC"))
    )
}