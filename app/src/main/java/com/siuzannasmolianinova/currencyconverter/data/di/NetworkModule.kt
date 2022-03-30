package com.siuzannasmolianinova.currencyconverter.data.di

import com.siuzannasmolianinova.currencyconverter.data.network.NetworkApi
import com.siuzannasmolianinova.currencyconverter.data.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { createApi(get()) }
    single { provideRetrofit(get()) }
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
    return builder.build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
    OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

private fun createApi(retrofit: Retrofit) = retrofit.create(NetworkApi::class.java)
