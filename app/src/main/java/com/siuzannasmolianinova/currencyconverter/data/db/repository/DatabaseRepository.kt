package com.siuzannasmolianinova.currencyconverter.data.db.repository

import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDto

interface DatabaseRepository {
    suspend fun writeAllCurrencies(currencies: List<CurrencyDto>)

    suspend fun readAllCurrenciesInfo(date: String): List<CurrencyDto>

    suspend fun readCurrencyInfo(id: String, date: String): CurrencyDto
}
