package com.example.erp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SaleDao {
    @Query("SELECT * FROM sales ORDER BY date DESC")
    fun getAllSales(): LiveData<List<Sale>>
    
    @Query("SELECT * FROM sales WHERE id = :saleId")
    fun getSale(saleId: Int): LiveData<Sale>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: Sale)
    
    @Update
    suspend fun update(sale: Sale)
    
    @Delete
    suspend fun delete(sale: Sale)
    
    @Query("SELECT * FROM sales WHERE date BETWEEN :startDate AND :endDate")
    fun getSalesByDateRange(startDate: Long, endDate: Long): LiveData<List<Sale>>
    
    @Query("SELECT SUM(totalPrice) FROM sales WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalSalesInRange(startDate: Long, endDate: Long): LiveData<Double>
    
    @Query("SELECT * FROM sales WHERE customerName LIKE :searchQuery OR customerPhone LIKE :searchQuery")
    fun searchSales(searchQuery: String): LiveData<List<Sale>>
} 