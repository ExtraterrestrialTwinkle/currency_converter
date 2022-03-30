package com.siuzannasmolianinova.currencyconverter.presentation.calc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siuzannasmolianinova.currencyconverter.data.CurrencyRepository
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency
import com.siuzannasmolianinova.currencyconverter.presentation.utils.toApiDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CalcViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val scope = viewModelScope
    private val _info = MutableLiveData<Currency>()
    private val _calculation = MutableLiveData<Double>()

    val info: LiveData<Currency> get() = _info
    val calculation: LiveData<Double> get() = _calculation

    fun onInitialized(id: String, date: String?) {
        scope.launch(Dispatchers.IO) {
            try {
                val actualDate = if (date.isNullOrEmpty()) {
                    System.currentTimeMillis().toApiDate()
                } else {
                    date
                }
                _info.postValue(repository.fetchData(id, actualDate))
            } catch (ex: Exception) {
                Timber.e(ex.message, ex)
            }
        }
    }

    fun calculate(denomination: Double, amountString: String) {
        if (amountString.isBlank()) return
        _calculation.postValue(denomination * amountString.toDouble())
    }
}
