package com.siuzannasmolianinova.currencyconverter.data.db.repository

import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDao
import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDto

class DatabaseRepositoryImpl(private val dao: CurrencyDao) : DatabaseRepository {
    override suspend fun writeAllCurrencies(currencies: List<CurrencyDto>) =
        dao.writeAllCurrencies(currencies)

    override suspend fun readAllCurrenciesInfo(date: String): List<CurrencyDto> =
        dao.readAllCurrenciesInfo(date)

    override suspend fun readCurrencyInfo(id: String, date: String): CurrencyDto =
        dao.readCurrencyInfo(id, date)
}
