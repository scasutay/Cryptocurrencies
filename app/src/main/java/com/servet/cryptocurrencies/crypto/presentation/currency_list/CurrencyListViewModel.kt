package com.servet.cryptocurrencies.crypto.presentation.currency_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.servet.cryptocurrencies.core.domain.util.onError
import com.servet.cryptocurrencies.core.domain.util.onSuccess
import com.servet.cryptocurrencies.crypto.domain.CryptoDataSource
import com.servet.cryptocurrencies.crypto.presentation.models.toCurrencyUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val cryptoDataSource: CryptoDataSource
): ViewModel() {

    private val _state = MutableStateFlow(CurrencyListState())
    val state = _state
        .onStart { loadCurrencies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CurrencyListState()
        )

    fun onAction(action: CurrencyListAction) {
        when (action) {
            is CurrencyListAction.OnCurrencyClick -> {

            }
        }
    }


    private fun loadCurrencies() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }

            cryptoDataSource.getCurrencies()
                .onSuccess { currencies ->
                    _state.update { it.copy(
                        isLoading = false,
                        currencies = currencies.map { it.toCurrencyUI() }
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}