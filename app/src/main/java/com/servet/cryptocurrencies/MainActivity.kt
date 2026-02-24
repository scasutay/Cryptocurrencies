package com.servet.cryptocurrencies

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.servet.cryptocurrencies.core.domain.util.toString
import com.servet.cryptocurrencies.core.presentation.util.ObserveAsEvents
import com.servet.cryptocurrencies.crypto.presentation.currency_detail.CurrencyDetailScreen
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListEvent
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListScreen
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListViewModel
import com.servet.cryptocurrencies.ui.theme.CryptocurrenciesTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptocurrenciesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CurrencyListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.events) { event ->
                        when (event) {
                            is CurrencyListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    event.error.toString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    when {
                        state.selectedCurrency != null -> {
                            CurrencyDetailScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        else -> {
                            CurrencyListScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding),
                                onAction = viewModel::onAction
                            )
                        }
                    }
                }
            }
        }
    }
}