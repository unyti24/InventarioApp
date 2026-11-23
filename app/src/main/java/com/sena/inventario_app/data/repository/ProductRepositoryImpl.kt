package com.sena.inventario_app.data.repository

import com.sena.inventario_app.data.local.ProductDao
import com.sena.inventario_app.data.mapper.toDomain
import com.sena.inventario_app.data.mapper.toEntity
import com.sena.inventario_app.domain.model.Product
import com.sena.inventario_app.domain.repository.ProductRepository
import com.sena.inventario_app.domain.repository.SyncReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {

    override fun observeProducts(): Flow<List<Product>> =
        dao.observeVisible().map { products ->
            products.map { it.toDomain() }
        }

    override suspend fun findById(id: String): Product? =
        dao.findById(id)?.takeUnless { it.isDeleted }?.toDomain()

    override suspend fun save(product: Product) {
        dao.upsert(product.copy(updatedAt = System.currentTimeMillis()).toEntity())
    }

    override suspend fun delete(id: String) {
        dao.markDeleted(id)
    }

    override suspend fun syncNow(): SyncReport {
        return SyncReport(uploaded = 0, downloaded = 0)
    }
}