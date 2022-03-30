package com.siuzannasmolianinova.currencyconverter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun writeAllCurrencies(currencies: List<CurrencyDto>)

    @Query("SELECT * FROM ${CurrencyContract.TABLE_NAME} WHERE ${CurrencyContract.Columns.DATE} =:date")
    fun readAllCurrenciesInfo(date: String): List<CurrencyDto>

    @Query("SELECT * FROM ${CurrencyContract.TABLE_NAME} WHERE ${CurrencyContract.Columns.ID} =:id AND ${CurrencyContract.Columns.DATE} =:date")
    fun readCurrencyInfo(id: String, date: String): CurrencyDto
}
