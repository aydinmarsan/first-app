package com.example.erp.ui.sales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.erp.data.ErpDatabase
import com.example.erp.data.Sale
import com.example.erp.repository.SaleRepository
import java.util.*

class SalesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SaleRepository
    private val _dateRange = MutableLiveData<Pair<Long, Long>>()

    init {
        val saleDao = ErpDatabase.getDatabase(application).saleDao()
        repository = SaleRepository(saleDao)
        
        // Set initial date range to current month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startDate = calendar.timeInMillis
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endDate = calendar.timeInMillis
        
        _dateRange.value = Pair(startDate, endDate)
    }

    val sales: LiveData<List<Sale>> = _dateRange.switchMap { range ->
        repository.getSalesByDateRange(range.first, range.second)
    }

    val totalSales: LiveData<Double> = _dateRange.switchMap { range ->
        repository.getTotalSalesInRange(range.first, range.second)
    }

    fun updateDateRange(startDate: Long, endDate: Long) {
        _dateRange.value = Pair(startDate, endDate)
    }

    fun addSale(sale: Sale) {
        repository.insert(sale)
    }

    fun updateSale(sale: Sale) {
        repository.update(sale)
    }

    fun deleteSale(sale: Sale) {
        repository.delete(sale)
    }

    fun searchSales(query: String): LiveData<List<Sale>> {
        return repository.searchSales(query)
    }
} 