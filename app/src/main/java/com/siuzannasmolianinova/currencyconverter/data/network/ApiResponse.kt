package com.siuzannasmolianinova.currencyconverter.data.network

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("Valute")
    val valute: Map<String, CurrencyResponse>
)

data class CurrencyResponse(
    @SerializedName("ID")
    val id: String,
    @SerializedName("NumCode")
    val numCode: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: Double,
    @SerializedName("Previous")
    val previous: Double
) {
    override fun toString(): String {
        return name + charCode
    }
}
