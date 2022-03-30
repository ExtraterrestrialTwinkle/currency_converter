package com.siuzannasmolianinova.currencyconverter.data.db

object CurrencyContract {
    const val TABLE_NAME = "currency"

    object Columns {
        const val ID = "id"
        const val DATE = "date"
        const val NAME = "name"
        const val NOMINAL = "nominal"
        const val CHAR_CODE = "charCode"
        const val VALUE = "value"
    }
}
