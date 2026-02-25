package com.servet.cryptocurrencies.crypto.domain

import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result
import java.time.ZonedDateTime

interface CryptoDataSource {

    suspend fun getCurrencies(): Result<List<Currency>, NetworkError>
    suspend fun getCurrencyHistory(
        currencyId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CurrencyPrice>, NetworkError>
}