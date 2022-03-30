package com.siuzannasmolianinova.currencyconverter.data

import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDto
import com.siuzannasmolianinova.currencyconverter.data.db.repository.DatabaseRepository
import com.siuzannasmolianinova.currencyconverter.data.network.repository.ApiRepository
import com.siuzannasmolianinova.currencyconverter.data.utils.toCurrency
import com.siuzannasmolianinova.currencyconverter.data.utils.toCurrencyDto
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency
import com.siuzannasmolianinova.currencyconverter.presentation.utils.toApiDate

class CurrencyRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository
) : CurrencyRepository {

    override suspend fun fetchData(date: String?): List<Currency> {
        val currentDate = date ?: System.currentTimeMillis().toApiDate()
        val listFromDb = databaseRepository.readAllCurrenciesInfo(currentDate)
        return if (listFromDb.isNullOrEmpty()) {
            apiRepository.loadCurrencies(date)
                .apply { writeData(this.map { it.toCurrencyDto(currentDate) }) }
                .map { it.toCurrency() }
        } else {
            listFromDb.map { it.toCurrency() }
        }
    }

    override suspend fun fetchData(id: String, date: String): Currency {
        return databaseRepository.readCurrencyInfo(id, date).toCurrency()
    }

    override suspend fun writeData(data: List<CurrencyDto>) {
        databaseRepository.writeAllCurrencies(data)
    }
}
