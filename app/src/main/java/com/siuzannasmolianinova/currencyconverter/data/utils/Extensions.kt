package com.siuzannasmolianinova.currencyconverter.data.utils

import com.siuzannasmolianinova.currencyconverter.data.db.CurrencyDto
import com.siuzannasmolianinova.currencyconverter.data.network.CurrencyResponse
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency

fun CurrencyResponse.toCurrency(): Currency = Currency(
    id = id,
    name = charCode,
    rate = value / nominal,
    fullName = name
)

fun CurrencyResponse.toCurrencyDto(date: String) = CurrencyDto(
    id = id,
    name = name,
    charCode = charCode,
    nominal = nominal,
    value = value,
    date = date
)

fun CurrencyDto.toCurrency() = Currency(
    id = id,
    name = charCode,
    rate = value / nominal,
    fullName = name
)
