package com.example.erp.ui.inventory

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.erp.databinding.FragmentInventoryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.erp.data.Product

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InventoryViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter { product ->
            showEditProductDialog(product)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@InventoryFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.allProducts.observe(viewLifecycleOwner, Observer { products ->
            adapter.submitList(products)
            binding.emptyView.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            showAddProductDialog()
        }
    }

    private fun showAddProductDialog() {
        AddEditProductDialog.newInstance()
            .show(childFragmentManager, "add_product")
    }

    private fun showEditProductDialog(product: Product) {
        AddEditProductDialog.newInstance(product)
            .show(childFragmentManager, "edit_product")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 