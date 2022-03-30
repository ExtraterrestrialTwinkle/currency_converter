package com.siuzannasmolianinova.currencyconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DB_VERSION = 1
const val DB_NAME = "currency_database"

@Database(
    entities = [
        CurrencyDto::class
    ],
    version = DB_VERSION
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
