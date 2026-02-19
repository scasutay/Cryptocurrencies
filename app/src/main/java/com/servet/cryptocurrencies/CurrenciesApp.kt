package com.servet.cryptocurrencies

import android.app.Application
import com.servet.cryptocurrencies.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrenciesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrenciesApp)
            androidLogger()
            modules(appModule)
        }
    }
}