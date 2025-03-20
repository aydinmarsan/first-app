package com.example.erp.ui.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.erp.data.ErpDatabase
import com.example.erp.data.Product
import com.example.erp.repository.ProductRepository
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = ErpDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.update(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun searchProducts(query: String): LiveData<List<Product>> {
        return repository.searchProducts(query)
    }
} 