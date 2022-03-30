package com.siuzannasmolianinova.currencyconverter.presentation.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.siuzannasmolianinova.currencyconverter.R
import com.siuzannasmolianinova.currencyconverter.databinding.FragmentCalcBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class CalcFragment : Fragment() {
    private var _binding: FragmentCalcBinding? = null
    private val binding: FragmentCalcBinding get() = _binding!!
    private val viewModel: CalcViewModel by viewModel()
    private val args: CalcFragmentArgs by navArgs()
    private var denomination: Double by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onInitialized(args.id, args.date)
        observeEvents()
        binding.foreignCurrency.editText?.doAfterTextChanged { data ->
            calculate(denomination, data.toString())
        }
        binding.foreignCurrency.editText?.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
                view.performClick()
            }
        }
        binding.foreignCurrency.editText?.setOnClickListener {
            binding.foreignCurrency.editText?.setText("")
        }
        binding.foreignCurrency.editText?.isClickable = false
    }

    private fun observeEvents() {
        viewModel.info.observe(viewLifecycleOwner) { currency ->
            binding.nameTextView.text = currency.fullName
            binding.exchangeRateTextView.text =
                getString(R.string.currency_rate_template, currency.rate)
            binding.foreignCurrency.hint = currency.name
            denomination = currency.rate
            (activity as AppCompatActivity).supportActionBar?.apply {
                title = currency.name
                setHomeAsUpIndicator(R.drawable.back)
                setDisplayHomeAsUpEnabled(true)
            }
        }

        viewModel.calculation.observe(viewLifecycleOwner) { result ->
            binding.rusCurrency.editText?.setText(
                getString(
                    R.string.currency_rate_template,
                    result
                )
            )
        }
    }

    private fun calculate(denomination: Double, data: String) {
        viewModel.calculate(denomination, data)
    }
}
