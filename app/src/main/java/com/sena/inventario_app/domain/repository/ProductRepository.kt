package com.sena.inventario_app.domain.repository

import com.sena.inventario_app.domain.model.Product
import kotlinx.coroutines.flow.Flow

data class SyncReport(
    val uploaded: Int,
    val downloaded: Int
)

interface ProductRepository {

    fun observeProducts(): Flow<List<Product>>

    suspend fun findById(id: String): Product?

    suspend fun save(product: Product)

    suspend fun delete(id: String)

    suspend fun syncNow(): SyncReport
}