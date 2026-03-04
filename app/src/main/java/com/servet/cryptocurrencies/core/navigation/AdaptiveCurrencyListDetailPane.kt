@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.servet.cryptocurrencies.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.servet.cryptocurrencies.core.domain.util.toString
import com.servet.cryptocurrencies.core.presentation.util.ObserveAsEvents
import com.servet.cryptocurrencies.crypto.presentation.currency_detail.CurrencyDetailScreen
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListAction
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListEvent
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListScreen
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCurrencyListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CurrencyListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    ObserveAsEvents(events = viewModel.events) { event ->
        when(event) {
            is CurrencyListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CurrencyListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action) {
                            is CurrencyListAction.OnCurrencyClick -> {
                                scope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail
                                    )
                                }
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CurrencyDetailScreen(state = state)
            }
        },
        modifier = modifier
    )
}