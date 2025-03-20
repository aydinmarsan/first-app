package com.example.erp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val code: String,
    val description: String,
    val unitPrice: Double,
    val stockQuantity: Int,
    val minimumStockLevel: Int,
    val category: String,
    val supplier: String,
    val lastUpdated: Long = System.currentTimeMillis()
) 