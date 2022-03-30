package com.siuzannasmolianinova.currencyconverter.core

import android.app.Application
import com.siuzannasmolianinova.currencyconverter.data.di.databaseModule
import com.siuzannasmolianinova.currencyconverter.data.di.networkModule
import com.siuzannasmolianinova.currencyconverter.data.di.repositoryModule
import com.siuzannasmolianinova.currencyconverter.presentation.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(provideModules())
        }
    }

    private fun provideModules() = listOf(
        viewModelModule,
        repositoryModule,
        networkModule,
        databaseModule
    )
}
