package com.siuzannasmolianinova.currencyconverter.data

import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDto
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency

interface CurrencyRepository {

    suspend fun fetchData(date: String?): List<Currency>

    suspend fun fetchData(id: String, date: String): Currency

    suspend fun writeData(data: List<CurrencyDto>)
}
