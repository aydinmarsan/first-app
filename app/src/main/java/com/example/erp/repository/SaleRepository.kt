package com.example.erp.repository

import androidx.lifecycle.LiveData
import com.example.erp.data.Sale
import com.example.erp.data.SaleDao

class SaleRepository(private val saleDao: SaleDao) {
    
    val allSales: LiveData<List<Sale>> = saleDao.getAllSales()
    
    suspend fun insert(sale: Sale) {
        saleDao.insert(sale)
    }
    
    suspend fun update(sale: Sale) {
        saleDao.update(sale)
    }
    
    suspend fun delete(sale: Sale) {
        saleDao.delete(sale)
    }
    
    fun getSale(id: Int): LiveData<Sale> {
        return saleDao.getSale(id)
    }
    
    fun getSalesByDateRange(startDate: Long, endDate: Long): LiveData<List<Sale>> {
        return saleDao.getSalesByDateRange(startDate, endDate)
    }
    
    fun getTotalSalesInRange(startDate: Long, endDate: Long): LiveData<Double> {
        return saleDao.getTotalSalesInRange(startDate, endDate)
    }
    
    fun searchSales(query: String): LiveData<List<Sale>> {
        return saleDao.searchSales("%$query%")
    }
} 