package com.example.erp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double,
    val customerName: String,
    val customerPhone: String,
    val date: Long = System.currentTimeMillis(),
    val notes: String = ""
) 