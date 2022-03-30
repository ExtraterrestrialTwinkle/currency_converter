package com.siuzannasmolianinova.currencyconverter.data.di

import android.app.Application
import androidx.room.Room
import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDatabase
import com.siuzannasmolianinova.currencyconverter.data.db.DB_NAME
import org.koin.dsl.module

val databaseModule = module {
    single { providesDao(get()) }
    single { providesDatabase(get()) }
}

private fun providesDatabase(application: Application): CurrencyDatabase {
    return Room.databaseBuilder(
        application,
        CurrencyDatabase::class.java,
        DB_NAME
    )
        .build()
}

private fun providesDao(db: CurrencyDatabase) = db.currencyDao()
