package com.siuzannasmolianinova.currencyconverter.presentation.entity

data class Currency(
    val id: String,
    val name: String,
    val rate: Double,
    val fullName: String
)
