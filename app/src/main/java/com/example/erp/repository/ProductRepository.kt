package com.example.erp.repository

import androidx.lifecycle.LiveData
import com.example.erp.data.Product
import com.example.erp.data.ProductDao

class ProductRepository(private val productDao: ProductDao) {
    
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()
    val lowStockProducts: LiveData<List<Product>> = productDao.getLowStockProducts()
    
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
    
    suspend fun update(product: Product) {
        productDao.update(product)
    }
    
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }
    
    fun getProduct(id: Int): LiveData<Product> {
        return productDao.getProduct(id)
    }
    
    fun searchProducts(query: String): LiveData<List<Product>> {
        return productDao.searchProducts("%$query%")
    }
} 