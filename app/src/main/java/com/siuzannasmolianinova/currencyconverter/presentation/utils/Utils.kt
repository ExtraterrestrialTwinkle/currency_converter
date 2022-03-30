package com.siuzannasmolianinova.currencyconverter.presentation.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.siuzannasmolianinova.currencyconverter.R
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat(DateConversion.DATE_FORMAT.format, Locale.getDefault())
    return format.format(date)
}

fun Long.toApiDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat(DateConversion.API_FORMAT.format, Locale.getDefault())
    return format.format(date)
}

fun <T : Fragment> T.snackbar(view: View, message: String): Snackbar =
    Snackbar.make(requireContext(), view, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(requireContext().getColor(R.color.gray_700))
        setTextColor(requireContext().getColor(R.color.white))
        setActionTextColor(requireContext().getColor(R.color.light_blue))
    }
