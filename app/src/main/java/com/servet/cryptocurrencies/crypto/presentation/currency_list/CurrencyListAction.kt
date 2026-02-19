package com.servet.cryptocurrencies.crypto.presentation.currency_list

import com.servet.cryptocurrencies.crypto.presentation.models.CurrencyUI

sealed interface CurrencyListAction {
    data class OnCurrencyClick(val currencyUI: CurrencyUI): CurrencyListAction
}