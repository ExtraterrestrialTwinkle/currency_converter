package com.siuzannasmolianinova.currencyconverter.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/daily_json.js")
    fun loadAllCurrencies(): Call<ApiResponse>

    @GET("/archive/{date}.js")
    fun loadArchiveCurrencies(
        @Path("date") date: String
    ): Call<ApiResponse>
}
