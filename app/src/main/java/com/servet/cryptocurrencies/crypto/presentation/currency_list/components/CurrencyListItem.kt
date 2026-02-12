package com.servet.cryptocurrencies.crypto.presentation.currency_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.crypto.presentation.models.CurrencyUI
import com.servet.cryptocurrencies.crypto.presentation.models.toCurrencyUI
import com.servet.cryptocurrencies.ui.theme.CryptocurrenciesTheme

@Composable
fun CurrencyListItem(
    currencyUI: CurrencyUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(currencyUI.iconResource),
            contentDescription = "${currencyUI.name} icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = currencyUI.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = currencyUI.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${currencyUI.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
            Spacer(Modifier.height(8.dp))
            PriceChange(change = currencyUI.changePercent24Hr)
        }
    }
}

@PreviewLightDark
@Composable
private fun CurrencyListItemPreview() {
    CryptocurrenciesTheme {
        CurrencyListItem(
            currencyUI = mockCurrency,
            onClick = { TODO() },
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

internal val mockCurrency = Currency(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = "1357204795088.08".toBigDecimal(),
    priceUsd = "70000.25".toBigDecimal(),
    changePercent24Hr = -2.5
).toCurrencyUI()