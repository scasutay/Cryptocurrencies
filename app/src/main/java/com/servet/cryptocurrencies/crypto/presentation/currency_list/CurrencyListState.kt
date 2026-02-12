package com.servet.cryptocurrencies.crypto.presentation.currency_list

import androidx.compose.runtime.Immutable
import com.servet.cryptocurrencies.crypto.presentation.models.CurrencyUI

@Immutable
data class CurrencyListState(
    val isLoading: Boolean = false,
    val currencies: List<CurrencyUI> = emptyList(),
    val selectedCurrency: CurrencyUI? = null
)