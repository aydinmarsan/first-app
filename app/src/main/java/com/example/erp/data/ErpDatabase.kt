package com.example.erp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class, Sale::class], version = 1, exportSchema = false)
abstract class ErpDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao
    
    companion object {
        @Volatile
        private var INSTANCE: ErpDatabase? = null
        
        fun getDatabase(context: Context): ErpDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ErpDatabase::class.java,
                    "erp_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 