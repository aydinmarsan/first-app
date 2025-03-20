package com.example.erp.ui.sales

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.erp.databinding.FragmentSalesBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class SalesFragment : Fragment() {

    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SalesViewModel by viewModels()
    private lateinit var adapter: SalesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupFab()
        setupDateRangePicker()
    }

    private fun setupRecyclerView() {
        adapter = SalesAdapter { sale ->
            showSaleDetailsDialog(sale)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SalesFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.sales.observe(viewLifecycleOwner, Observer { sales ->
            adapter.submitList(sales)
            binding.emptyView.visibility = if (sales.isEmpty()) View.VISIBLE else View.GONE
        })

        viewModel.totalSales.observe(viewLifecycleOwner, Observer { total ->
            binding.textTotalSales.text = String.format("Toplam Satış: ₺%.2f", total)
        })
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showAddSaleDialog()
        }
    }

    private fun setupDateRangePicker() {
        binding.buttonDateRange.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Tarih Aralığı Seçin")
                .build()

            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second
                viewModel.updateDateRange(startDate, endDate)
                
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val startDateStr = dateFormat.format(Date(startDate))
                val endDateStr = dateFormat.format(Date(endDate))
                binding.textDateRange.text = "$startDateStr - $endDateStr"
            }

            dateRangePicker.show(childFragmentManager, "date_range_picker")
        }
    }

    private fun showAddSaleDialog() {
        AddEditSaleDialog.newInstance()
            .show(childFragmentManager, "add_sale")
    }

    private fun showSaleDetailsDialog(sale: Sale) {
        SaleDetailsDialog.newInstance(sale)
            .show(childFragmentManager, "sale_details")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 