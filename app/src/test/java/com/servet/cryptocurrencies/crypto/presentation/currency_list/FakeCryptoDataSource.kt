package com.servet.cryptocurrencies.crypto.presentation.currency_list

import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result
import com.servet.cryptocurrencies.crypto.domain.CryptoDataSource
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.crypto.domain.CurrencyPrice
import java.time.ZonedDateTime

class FakeCryptoDataSource : CryptoDataSource {

    var currenciesResult: Result<List<Currency>, NetworkError> = Result.Success(emptyList())

    var currencyHistoryResult: Result<List<CurrencyPrice>, NetworkError> =
        Result.Success(emptyList())

    var requestedCurrencyId: String? = null
    var requestedStart: ZonedDateTime? = null
    var requestedEnd: ZonedDateTime? = null

    override suspend fun getCurrencies(): Result<List<Currency>, NetworkError> {
        return currenciesResult
    }

    override suspend fun getCurrencyHistory(
        currencyId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CurrencyPrice>, NetworkError> {
        requestedCurrencyId = currencyId
        requestedStart = start
        requestedEnd = end
        return currencyHistoryResult
    }
}