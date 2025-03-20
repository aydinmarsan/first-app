package com.example.erp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProducts(): LiveData<List<Product>>
    
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProduct(productId: Int): LiveData<Product>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)
    
    @Update
    suspend fun update(product: Product)
    
    @Delete
    suspend fun delete(product: Product)
    
    @Query("SELECT * FROM products WHERE stockQuantity <= minimumStockLevel")
    fun getLowStockProducts(): LiveData<List<Product>>
    
    @Query("SELECT * FROM products WHERE name LIKE :searchQuery OR code LIKE :searchQuery")
    fun searchProducts(searchQuery: String): LiveData<List<Product>>
} 