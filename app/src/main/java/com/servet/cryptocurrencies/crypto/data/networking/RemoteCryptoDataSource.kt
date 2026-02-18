package com.servet.cryptocurrencies.crypto.data.networking

import com.servet.cryptocurrencies.core.data.networking.constructUrl
import com.servet.cryptocurrencies.core.data.networking.safeCall
import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result
import com.servet.cryptocurrencies.core.domain.util.map
import com.servet.cryptocurrencies.crypto.data.mapper.toCurrency
import com.servet.cryptocurrencies.crypto.data.networking.dto.CurrencyResponseDto
import com.servet.cryptocurrencies.crypto.domain.CryptoDataSource
import com.servet.cryptocurrencies.crypto.domain.Currency
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCryptoDataSource(
    private val httpClient: HttpClient
): CryptoDataSource {

    override suspend fun getCurrencies(): Result<List<Currency>, NetworkError> {
        return safeCall<CurrencyResponseDto> {
            httpClient.get(urlString = constructUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCurrency() }
        }
    }
}