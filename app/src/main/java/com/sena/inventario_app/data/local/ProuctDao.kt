package com.sena.inventario_app.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeVisible(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): ProductEntity?

    @Query("SELECT * FROM products WHERE pending = 1")
    suspend fun getPending(): List<ProductEntity>

    @Upsert
    suspend fun upsert(product: ProductEntity)

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("UPDATE products SET isDeleted = 1, pending = 1 WHERE id = :id")
    suspend fun markDeleted(id: String)

    @Query("DELETE FROM products")
    suspend fun clearAll()
}