package com.siuzannasmolianinova.currencyconverter.presentation.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.siuzannasmolianinova.currencyconverter.R
import com.siuzannasmolianinova.currencyconverter.databinding.FragmentCurrencyListBinding
import com.siuzannasmolianinova.currencyconverter.presentation.utils.snackbar
import com.siuzannasmolianinova.currencyconverter.presentation.utils.toApiDate
import com.siuzannasmolianinova.currencyconverter.presentation.utils.toDateString
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private const val DATE_PICKER_TAG = "Date Picker"

class CurrencyListFragment : Fragment() {
    private var _binding: FragmentCurrencyListBinding? = null
    private val binding: FragmentCurrencyListBinding get() = _binding!!
    private lateinit var rvAdapter: CurrencyTableAdapter
    private val currencyListViewModel: CurrencyListViewModel by viewModel()
    private var loader: AlertDialog? = null
    private var date: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        currencyListViewModel.onInitialized(date)
        observeEvents()
        binding.editText.setText(R.string.dd_mm_yyyy)
        binding.editText.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
                view.performClick()
            }
        }
        binding.editText.setOnClickListener {
            showDatePicker()
        }
    }

    private fun initAdapter() {
        rvAdapter = CurrencyTableAdapter { id ->
            findNavController().navigate(
                CurrencyListFragmentDirections.actionCurrencyListFragmentToCalcFragment(
                    id = id,
                    date = date?.toApiDate()
                )
            )
            Timber.d("Click! $id, $date")
        }
        binding.currencyTable.run {
            adapter = rvAdapter
            addItemDecoration(ItemOffsetDecoration(requireContext()))
        }
    }

    private fun observeEvents() {
        currencyListViewModel.currencyList.observe(viewLifecycleOwner) { currencyList ->
            Timber.d("CurrencyList = $currencyList")
            rvAdapter.submitList(currencyList)
        }
        currencyListViewModel.error.observe(viewLifecycleOwner) { ex ->
            Timber.e(ex.message, ex)
            ex.message?.let { snackbar(binding.root, it).show() }
            binding.editText.setText(R.string.dd_mm_yyyy)
            binding.editText.clearFocus()
            date = null
        }
        currencyListViewModel.noConnection.observe(viewLifecycleOwner) { ex ->
            Timber.e("noConnection", ex.message, ex)
            ex.message?.let { message ->
                snackbar(binding.root, message)
                    .setDuration(Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry)) {
                        currencyListViewModel.onInitialized(date)
                    }
                    .show()
            }
        }
        currencyListViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoader(isLoading)
        }
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.date_picker))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
            .apply {
                addOnPositiveButtonClickListener { timestamp ->
                    date = timestamp
                    val dateString = timestamp.toDateString()
                    binding.editText.setText(dateString)
                    currencyListViewModel.onInitialized(timestamp)
                }
                addOnNegativeButtonClickListener {
                    dismiss()
                }
            }
            .show(childFragmentManager, DATE_PICKER_TAG)
    }

    private fun showLoader(isLoading: Boolean) {
        Timber.d("Loader")
        if (loader == null) createLoader()
        if (isLoading) {
            loader?.show()
        } else {
            loader?.dismiss()
        }
    }

    private fun createLoader() {
        loader = AlertDialog.Builder(requireContext())
            .setView(R.layout.loader)
            .create()
    }

    override fun onDestroyView() {
        Timber.d("OnDestroyView")
        super.onDestroyView()
        _binding = null
        loader = null
    }
}
