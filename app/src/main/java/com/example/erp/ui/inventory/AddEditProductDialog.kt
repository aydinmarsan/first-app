package com.example.erp.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.erp.data.Product
import com.example.erp.databinding.DialogAddEditProductBinding

class AddEditProductDialog : DialogFragment() {

    private var _binding: DialogAddEditProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InventoryViewModel by viewModels()
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product?.let { setupEditMode(it) }
        setupButtons()
    }

    private fun setupEditMode(product: Product) {
        binding.apply {
            editName.setText(product.name)
            editCode.setText(product.code)
            editDescription.setText(product.description)
            editPrice.setText(product.unitPrice.toString())
            editStock.setText(product.stockQuantity.toString())
            editMinStock.setText(product.minimumStockLevel.toString())
            editCategory.setText(product.category)
            editSupplier.setText(product.supplier)
        }
    }

    private fun setupButtons() {
        binding.buttonSave.setOnClickListener {
            if (validateInputs()) {
                saveProduct()
                dismiss()
            }
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        binding.apply {
            if (editName.text.isNullOrBlank()) {
                editName.error = "Ürün adı gerekli"
                isValid = false
            }
            if (editCode.text.isNullOrBlank()) {
                editCode.error = "Ürün kodu gerekli"
                isValid = false
            }
            if (editPrice.text.isNullOrBlank()) {
                editPrice.error = "Fiyat gerekli"
                isValid = false
            }
            if (editStock.text.isNullOrBlank()) {
                editStock.error = "Stok miktarı gerekli"
                isValid = false
            }
        }
        return isValid
    }

    private fun saveProduct() {
        val newProduct = Product(
            id = product?.id ?: 0,
            name = binding.editName.text.toString(),
            code = binding.editCode.text.toString(),
            description = binding.editDescription.text.toString(),
            unitPrice = binding.editPrice.text.toString().toDoubleOrNull() ?: 0.0,
            stockQuantity = binding.editStock.text.toString().toIntOrNull() ?: 0,
            minimumStockLevel = binding.editMinStock.text.toString().toIntOrNull() ?: 0,
            category = binding.editCategory.text.toString(),
            supplier = binding.editSupplier.text.toString()
        )

        if (product == null) {
            viewModel.addProduct(newProduct)
        } else {
            viewModel.updateProduct(newProduct)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product? = null): AddEditProductDialog {
            return AddEditProductDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, product)
                }
            }
        }
    }
} 