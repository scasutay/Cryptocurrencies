package com.servet.cryptocurrencies.crypto.presentation.currency_detail

import android.icu.text.IDNA.Info
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servet.cryptocurrencies.R
import com.servet.cryptocurrencies.core.domain.extensions.toBigDecimal
import com.servet.cryptocurrencies.crypto.presentation.currency_detail.components.InfoCard
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListState
import com.servet.cryptocurrencies.crypto.presentation.currency_list.components.mockCurrency
import com.servet.cryptocurrencies.crypto.presentation.models.formatAsCurrency
import com.servet.cryptocurrencies.ui.theme.CryptocurrenciesTheme
import com.servet.cryptocurrencies.ui.theme.greenBackground
import java.math.BigDecimal

@Composable
fun CurrencyDetailScreen(
    state: CurrencyListState,
    modifier: Modifier = Modifier
) {

    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCurrency != null) {
        val currency = state.selectedCurrency
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = currency.iconResource),
                contentDescription = currency.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = currency.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            Text(
                text = currency.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formattedText = "$ ${currency.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                InfoCard(
                    title = stringResource(R.string.price),
                    formattedText = "$ ${currency.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )
                val absoluteChange = currency.priceUsd.value.multiply(
                    currency.changePercent24Hr.value
                        .toBigDecimal()
                        .divide(BigDecimal.valueOf(100))
                ).formatAsCurrency()
                val isPositive = currency.changePercent24Hr.value >= 0.0
                val contentColor  = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(R.string.change_last_24h),
                    formattedText = absoluteChange.formatted,
                    icon = if (isPositive) {
                        ImageVector.vectorResource(id = R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CurrencyDetailScreenPreview() {
    CryptocurrenciesTheme {
        CurrencyDetailScreen(
            state = CurrencyListState(
                selectedCurrency = mockCurrency
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}