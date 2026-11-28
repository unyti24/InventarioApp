package com.sena.inventario_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sena.inventario_app.data.local.ProductDao
import com.sena.inventario_app.data.local.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}