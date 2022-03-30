package com.siuzannasmolianinova.currencyconverter.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = [
        CurrencyContract.Columns.ID,
        CurrencyContract.Columns.DATE
    ],
    tableName = CurrencyContract.TABLE_NAME
)
data class CurrencyDto(
    @ColumnInfo(name = CurrencyContract.Columns.ID) val id: String,
    @ColumnInfo(name = CurrencyContract.Columns.NAME) val name: String,
    @ColumnInfo(name = CurrencyContract.Columns.NOMINAL) val nominal: Int,
    @ColumnInfo(name = CurrencyContract.Columns.CHAR_CODE) val charCode: String,
    @ColumnInfo(name = CurrencyContract.Columns.VALUE) val value: Double,
    @ColumnInfo(name = CurrencyContract.Columns.DATE) val date: String
)
