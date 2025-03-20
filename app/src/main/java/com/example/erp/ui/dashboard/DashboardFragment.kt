package com.example.erp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.erp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lowStockProducts.observe(viewLifecycleOwner, Observer { products ->
            binding.textLowStockCount.text = "${products.size} ürün kritik stok seviyesinde"
        })

        viewModel.totalProducts.observe(viewLifecycleOwner, Observer { count ->
            binding.textTotalProducts.text = "Toplam $count ürün"
        })

        viewModel.totalValue.observe(viewLifecycleOwner, Observer { value ->
            binding.textTotalValue.text = "Toplam Stok Değeri: ₺${String.format("%.2f", value)}"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 