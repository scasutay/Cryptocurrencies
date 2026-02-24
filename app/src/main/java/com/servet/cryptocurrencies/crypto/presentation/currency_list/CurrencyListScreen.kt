package com.servet.cryptocurrencies.crypto.presentation.currency_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.servet.cryptocurrencies.crypto.presentation.currency_list.components.CurrencyListItem
import com.servet.cryptocurrencies.crypto.presentation.currency_list.components.mockCurrency
import com.servet.cryptocurrencies.ui.theme.CryptocurrenciesTheme

@Composable
fun CurrencyListScreen(
    state: CurrencyListState,
    onAction: (CurrencyListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.currencies) { currency ->
                CurrencyListItem(
                    currencyUI = currency,
                    onClick = {
                        onAction(CurrencyListAction.OnCurrencyClick(currency))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun CurrencyListScreenPreview() {
    CryptocurrenciesTheme {
        CurrencyListScreen(
            state = CurrencyListState(
                currencies = (1..10).map { mockCurrency.copy(id = it.toString()) }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}