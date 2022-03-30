package com.siuzannasmolianinova.currencyconverter.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.currencyconverter.data.CurrencyRepository
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency
import com.siuzannasmolianinova.currencyconverter.presentation.utils.SingleLiveEvent
import com.siuzannasmolianinova.currencyconverter.presentation.utils.toApiDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException

class CurrencyListViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val scope = viewModelScope
    private val _currencyList = MutableLiveData<List<Currency>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _error = SingleLiveEvent<Exception>()
    private val _noConnection = SingleLiveEvent<Exception>()

    val currencyList: LiveData<List<Currency>> get() = _currencyList
    val isLoading: LiveData<Boolean> get() = _isLoading
    val error: LiveData<Exception> get() = _error
    val noConnection: LiveData<Exception> get() = _noConnection

    fun onInitialized(date: Long?) {
        scope.launch(Dispatchers.IO) {
            val apiDate = date?.toApiDate()
            try {
                _isLoading.postValue(true)
                _currencyList.postValue(
                    repository.fetchData(apiDate)
                )
            } catch (ex: ConnectException) {
                _noConnection.postValue(ex)
            } catch (ex: IOException) {
                _error.postValue(ex)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
