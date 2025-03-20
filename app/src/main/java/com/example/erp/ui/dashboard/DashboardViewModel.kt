package com.example.erp.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.erp.data.ErpDatabase
import com.example.erp.data.Product
import com.example.erp.repository.ProductRepository
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ProductRepository
    val lowStockProducts: LiveData<List<Product>>
    private val _totalProducts = MutableLiveData<Int>()
    val totalProducts: LiveData<Int> = _totalProducts
    private val _totalValue = MutableLiveData<Double>()
    val totalValue: LiveData<Double> = _totalValue

    init {
        val productDao = ErpDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        lowStockProducts = repository.lowStockProducts
        
        viewModelScope.launch {
            updateDashboardData()
        }
    }

    private suspend fun updateDashboardData() {
        repository.allProducts.observeForever { products ->
            _totalProducts.value = products.size
            _totalValue.value = products.sumOf { it.unitPrice * it.stockQuantity }
        }
    }
} 