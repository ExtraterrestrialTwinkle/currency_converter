package com.siuzannasmolianinova.currencyconverter.presentation.di

import com.siuzannasmolianinova.currencyconverter.presentation.calc.CalcViewModel
import com.siuzannasmolianinova.currencyconverter.presentation.list.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CurrencyListViewModel(get()) }
    viewModel { CalcViewModel(get()) }
}
