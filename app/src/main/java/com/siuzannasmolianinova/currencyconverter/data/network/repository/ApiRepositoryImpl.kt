package com.siuzannasmolianinova.currencyconverter.data.network.repository

import com.siuzannasmolianinova.currencyconverter.data.network.ApiResponse
import com.siuzannasmolianinova.currencyconverter.data.network.CurrencyResponse
import com.siuzannasmolianinova.currencyconverter.data.network.NetworkApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApiRepositoryImpl(private val api: NetworkApi) : ApiRepository {

    override suspend fun loadCurrencies(date: String?): List<CurrencyResponse> {
        return suspendCoroutine { continuation ->
            val request =
                if (date != null) {
                    api.loadArchiveCurrencies(date)
                } else {
                    api.loadAllCurrencies()
                }
            request.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()
                            ?.valute
                            ?.map { it.value }?.let {
                                continuation.resume(it)
                            }
                    } else {
                        continuation.resumeWithException(IOException("Страница не найдена"))
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    continuation.resumeWithException(ConnectException("Нет соединения с сервером"))
                }
            })
        }
    }
}
