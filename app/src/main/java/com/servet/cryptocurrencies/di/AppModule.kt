package com.servet.cryptocurrencies.di

import com.servet.cryptocurrencies.core.data.networking.HttpClientFactory
import com.servet.cryptocurrencies.crypto.data.networking.RemoteCryptoDataSource
import com.servet.cryptocurrencies.crypto.domain.CryptoDataSource
import com.servet.cryptocurrencies.crypto.presentation.currency_list.CurrencyListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCryptoDataSource).bind<CryptoDataSource>()

    viewModelOf(::CurrencyListViewModel)
}