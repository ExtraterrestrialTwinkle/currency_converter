package com.siuzannasmolianinova.currencyconverter.data.di

import com.siuzannasmolianinova.currencyconverter.data.CurrencyRepository
import com.siuzannasmolianinova.currencyconverter.data.CurrencyRepositoryImpl
import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDao
import com.siuzannasmolianinova.currencyconverter.data.db.repository.DatabaseRepository
import com.siuzannasmolianinova.currencyconverter.data.db.repository.DatabaseRepositoryImpl
import com.siuzannasmolianinova.currencyconverter.data.network.NetworkApi
import com.siuzannasmolianinova.currencyconverter.data.network.repository.ApiRepository
import com.siuzannasmolianinova.currencyconverter.data.network.repository.ApiRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<CurrencyRepository> { provideCurrencyRepository(get(), get()) }
    single<DatabaseRepository> { providesDatabaseRepository(get()) }
    single<ApiRepository> { provideApiRepository(get()) }
}

private fun provideCurrencyRepository(
    apiRepository: ApiRepository,
    databaseRepository: DatabaseRepository
): CurrencyRepository {
    return CurrencyRepositoryImpl(apiRepository, databaseRepository)
}

private fun providesDatabaseRepository(dao: CurrencyDao): DatabaseRepository {
    return DatabaseRepositoryImpl(dao)
}

private fun provideApiRepository(api: NetworkApi): ApiRepository {
    return ApiRepositoryImpl(api)
}
