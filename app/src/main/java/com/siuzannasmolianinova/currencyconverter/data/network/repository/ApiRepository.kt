package com.siuzannasmolianinova.currencyconverter.data.network.repository

import com.siuzannasmolianinova.currencyconverter.data.network.CurrencyResponse

interface ApiRepository {

    suspend fun loadCurrencies(date: String?): List<CurrencyResponse>
}
