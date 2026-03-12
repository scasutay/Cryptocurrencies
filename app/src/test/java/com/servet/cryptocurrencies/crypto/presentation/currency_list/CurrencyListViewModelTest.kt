package com.servet.cryptocurrencies.crypto.presentation.currency_list

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.servet.cryptocurrencies.core.domain.util.NetworkError
import com.servet.cryptocurrencies.core.domain.util.Result
import com.servet.cryptocurrencies.crypto.domain.Currency
import com.servet.cryptocurrencies.crypto.domain.CurrencyPrice
import com.servet.cryptocurrencies.test.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should load currencies into state when data source returns success`() = runTest {
        val fakeDataSource = FakeCryptoDataSource().apply {
            currenciesResult = Result.Success(
                listOf(
                    Currency(
                        id = "bitcoin",
                        rank = 1,
                        name = "Bitcoin",
                        symbol = "BTC",
                        marketCapUsd = BigDecimal("1000000"),
                        priceUsd = BigDecimal("54321.9"),
                        changePercent24Hr = 2.34
                    ),
                    Currency(
                        id = "ethereum",
                        rank = 2,
                        name = "Ethereum",
                        symbol = "ETH",
                        marketCapUsd = BigDecimal("500000"),
                        priceUsd = BigDecimal("3210.5"),
                        changePercent24Hr = -1.25
                    )
                )
            )
        }

        val viewModel = CurrencyListViewModel(fakeDataSource)

        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {  }
        }

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.currencies).hasSize(2)

        assertThat(state.currencies[0].id).isEqualTo("bitcoin")
        assertThat(state.currencies[0].name).isEqualTo("Bitcoin")
        assertThat(state.currencies[0].symbol).isEqualTo("BTC")

        assertThat(state.currencies[1].id).isEqualTo("ethereum")
        assertThat(state.currencies[1].name).isEqualTo("Ethereum")
        assertThat(state.currencies[1].symbol).isEqualTo("ETH")

        collectJob.cancel()
    }

    @Test
    fun `should emit error event when loading currencies fails`() = runTest {
        val fakeDataSource = FakeCryptoDataSource().apply {
            currenciesResult = Result.Error(NetworkError.SERVER_ERROR)
        }

        val viewModel = CurrencyListViewModel(fakeDataSource)

        val eventDeferred = async {
            viewModel.events.first()
        }

        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {  }
        }

        advanceUntilIdle()

        val event = eventDeferred.await()
        val state = viewModel.state.value

        assertThat(event).isEqualTo(CurrencyListEvent.Error(NetworkError.SERVER_ERROR))
        assertThat(state.isLoading).isFalse()
        assertThat(state.currencies).isEmpty()

        collectJob.cancel()
    }

    @Test
    fun `should load currency history when currency is clicked`() = runTest {
        val fakeDataSource = FakeCryptoDataSource().apply {
            currenciesResult = Result.Success(
                listOf(
                    Currency(
                        id = "bitcoin",
                        rank = 1,
                        name = "Bitcoin",
                        symbol = "BTC",
                        marketCapUsd = BigDecimal("1000000"),
                        priceUsd = BigDecimal("50000"),
                        changePercent24Hr = 2.0
                    )
                )
            )

            currencyHistoryResult = Result.Success(
                listOf(
                    CurrencyPrice(
                        priceUsd = BigDecimal("50000"),
                        dateTime = ZonedDateTime.parse("2026-01-01T10:00:00Z")
                    ),
                    CurrencyPrice(
                        priceUsd = BigDecimal("51000"),
                        dateTime = ZonedDateTime.parse("2026-01-01T10:00:00Z")
                    )
                )
            )
        }

        val viewModel = CurrencyListViewModel(fakeDataSource)

        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {  }
        }

        advanceUntilIdle()

        val  currency = viewModel.state.value.currencies.first()

        viewModel.onAction(CurrencyListAction.OnCurrencyClick(currency))

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.selectedCurrency?.id).isEqualTo("bitcoin")
        assertThat(state.selectedCurrency?.currencyPriceHistory).hasSize(2)

        val firstPoint = state.selectedCurrency?.currencyPriceHistory?.first()
        assertThat(firstPoint?.y).isEqualTo(50000f)

        collectJob.cancel()
    }

    @Test
    fun `should emit error when history loading fails`() = runTest {
        val fakeDataSource = FakeCryptoDataSource().apply {
            currenciesResult = Result.Success(
                listOf(
                    Currency(
                        id = "bitcoin",
                        rank = 1,
                        name = "Bitcoin",
                        symbol = "BTC",
                        marketCapUsd = BigDecimal("1000000"),
                        priceUsd = BigDecimal("50000"),
                        changePercent24Hr = 2.0
                    )
                )
            )

            currencyHistoryResult = Result.Error(NetworkError.SERVER_ERROR)
        }

        val viewModel = CurrencyListViewModel(fakeDataSource)

        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {  }
        }

        advanceUntilIdle()

        val selectedCurrency = viewModel.state.value.currencies.first()

        val eventDeferred = async {
            viewModel.events.first()
        }

        viewModel.onAction(CurrencyListAction.OnCurrencyClick(selectedCurrency))

        advanceUntilIdle()

        val event = eventDeferred.await()
        val state = viewModel.state.value

        assertThat(fakeDataSource.requestedCurrencyId).isEqualTo("bitcoin")
        assertThat(event).isEqualTo(CurrencyListEvent.Error(NetworkError.SERVER_ERROR))
        assertThat(state.selectedCurrency?.id).isEqualTo("bitcoin")
        assertThat(state.selectedCurrency?.currencyPriceHistory).isEmpty()

        collectJob.cancel()
    }
}