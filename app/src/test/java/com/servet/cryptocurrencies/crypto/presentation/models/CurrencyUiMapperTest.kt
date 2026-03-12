package com.servet.cryptocurrencies.crypto.presentation.models

import com.google.common.truth.Truth.assertThat
import com.servet.cryptocurrencies.crypto.domain.Currency
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.Locale

class CurrencyUiMapperTest {

    private var defaultLocale: Locale? = null

    @Before
    fun setUp() {
        defaultLocale = Locale.getDefault()
        Locale.setDefault(Locale.US)
    }

    @After
    fun tearDown() {
        defaultLocale?.let(Locale::setDefault)
    }

    @Test
    fun `formatAsCurrency should keep original value and format with two decimals`() {
        val amount = BigDecimal("12345.6")

        val result = amount.formatAsCurrency()

        assertThat(result.value).isEqualTo(BigDecimal("12345.6"))
        assertThat(result.formatted).isEqualTo("12,345.60")
    }

    @Test
    fun `formatAsPercentage should keep original value and format with two decimals`() {
        val percentage = 7.1

        val result = percentage.formatAsPercentage()

        assertThat(result.value).isEqualTo(7.1)
        assertThat(result.formatted).isEqualTo("7.10")
    }

    @Test
    fun `toCurrencyUI should map domain model fields correctly`() {
        val currency = Currency(
            id = "bitcoin",
            rank = 1,
            name = "Bitcoin",
            symbol = "BTC",
            marketCapUsd = BigDecimal("1000000"),
            priceUsd = BigDecimal("54321.9"),
            changePercent24Hr = 2.34
        )

        val result = currency.toCurrencyUI()

        assertThat(result.id).isEqualTo("bitcoin")
        assertThat(result.rank).isEqualTo(1)
        assertThat(result.name).isEqualTo("Bitcoin")
        assertThat(result.symbol).isEqualTo("BTC")

        assertThat(result.marketCapUsd.value).isEqualTo(BigDecimal("1000000"))
        assertThat(result.marketCapUsd.formatted).isEqualTo("1,000,000.00")

        assertThat(result.priceUsd.value).isEqualTo(BigDecimal("54321.9"))
        assertThat(result.priceUsd.formatted).isEqualTo("54,321.90")

        assertThat(result.changePercent24Hr.value).isEqualTo(2.34)
        assertThat(result.changePercent24Hr.formatted).isEqualTo("2.34")
    }

    @Test
    fun `formatAsPercentage should format negative values correctly`() {
        val percentage = -12.345

        val result = percentage.formatAsPercentage()

        assertThat(result.value).isEqualTo(-12.345)
        assertThat(result.formatted).isEqualTo("-12.35")
    }
}