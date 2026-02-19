package com.servet.cryptocurrencies.crypto.presentation.currency_list

import com.servet.cryptocurrencies.core.domain.util.NetworkError

sealed interface CurrencyListEvent {
    data class Error(val error: NetworkError): CurrencyListEvent
}