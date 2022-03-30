package com.siuzannasmolianinova.currencyconverter.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siuzannasmolianinova.currencyconverter.R
import com.siuzannasmolianinova.currencyconverter.databinding.ItemCurrencyCardBinding
import com.siuzannasmolianinova.currencyconverter.presentation.entity.Currency
import timber.log.Timber

class CurrencyTableAdapter(
    private val click: (String) -> Unit
) : ListAdapter<Currency, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCurrencyCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, click)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(currentList[position])
    }

    inner class NewsViewHolder(
        private val binding: ItemCurrencyCardBinding,
        click: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItemId: String? = null

        init {
            binding.root.setOnClickListener {
                this.currentItemId?.let { click(it) }
            }
        }

        fun bind(currency: Currency) {
            Timber.d("VH bind")
            binding.run {
                currentItemId = currency.id
                currencyNameTextView.text = currency.name
                currencyRateTextView.text =
                    binding.root.context.getString(R.string.currency_rate_template, currency.rate)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return newItem.name == oldItem.name && newItem.rate == oldItem.rate
        }
    }
}
