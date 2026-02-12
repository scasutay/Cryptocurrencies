package com.servet.cryptocurrencies.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.ui.util.getDrawableForCurrencyCode
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

data class CurrencyUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: FormattedAmount,
    val priceUsd: FormattedAmount,
    val changePercent24Hr: FormattedNumber,
    @DrawableRes val iconResource: Int
)

data class FormattedAmount(
    val value: BigDecimal,
    val formatted: String
)

data class FormattedNumber(
    val value: Double,
    val formatted: String
)

fun Currency.toCurrencyUI(): CurrencyUI {
    return CurrencyUI(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.formatAsCurrency(),
        marketCapUsd = marketCapUsd.formatAsCurrency(),
        changePercent24Hr = changePercent24Hr.formatAsPercentage(),
        iconResource = getDrawableForCurrencyCode(symbol)
        )
}

fun BigDecimal.formatAsCurrency(): FormattedAmount {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return FormattedAmount(
        value = this,
        formatted = formatter.format(this)
    )
}

fun Double.formatAsPercentage(): FormattedNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return FormattedNumber(
        value = this,
        formatted = formatter.format(this)
    )
}
