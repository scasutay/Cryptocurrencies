package com.servet.cryptocurrencies.crypto.domain

import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result

interface CryptoDataSource {

    suspend fun getCurrencies(): Result<List<Currency>, NetworkError>
}