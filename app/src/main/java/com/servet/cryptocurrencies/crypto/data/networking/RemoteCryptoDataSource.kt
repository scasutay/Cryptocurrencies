package com.servet.cryptocurrencies.crypto.data.networking

import com.servet.cryptocurrencies.core.data.networking.constructUrl
import com.servet.cryptocurrencies.core.data.networking.safeCall
import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result
import com.servet.cryptocurrencies.core.domain.util.map
import com.servet.cryptocurrencies.crypto.data.mapper.toCurrency
import com.servet.cryptocurrencies.crypto.data.mapper.toCurrencyPrice
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyHistoryResponseDto
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyResponseDto
import com.servet.cryptocurrencies.crypto.domain.CryptoDataSource
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.crypto.domain.CurrencyPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCryptoDataSource(
    private val httpClient: HttpClient
) : CryptoDataSource {

    override suspend fun getCurrencies(): Result<List<Currency>, NetworkError> {
        return safeCall<CurrencyResponseDto> {
            httpClient.get(urlString = constructUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCurrency() }
        }
    }

    override suspend fun getCurrencyHistory(
        currencyId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CurrencyPrice>, NetworkError> {
        val startInMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endInMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()

        return safeCall<CurrencyHistoryResponseDto> {
            httpClient.get(urlString = constructUrl("/assets/$currencyId/history")) {
                parameter("interval", "h6")
                parameter("start", startInMillis)
                parameter("end", endInMillis)
            }
        }.map { response ->
            response.data.map { it.toCurrencyPrice() }
        }
    }
}