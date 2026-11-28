package com.sena.inventario_app.data.repository

import com.sena.inventario_app.data.local.ProductDao
import com.sena.inventario_app.data.mapper.toDomain
import com.sena.inventario_app.data.mapper.toEntity
import com.sena.inventario_app.data.mapper.toDto
import com.sena.inventario_app.data.mapper.toEntity as remoteToEntity
import com.sena.inventario_app.data.remote.FirebaseAuthManager
import com.sena.inventario_app.data.remote.FirestoreProductDataSource
import com.sena.inventario_app.domain.model.Product
import com.sena.inventario_app.domain.repository.ProductRepository
import com.sena.inventario_app.domain.repository.SyncReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val authManager: FirebaseAuthManager,
    private val remote: FirestoreProductDataSource
) : ProductRepository {

    override fun observeProducts(): Flow<List<Product>> =
        dao.observeVisible().map { products ->
            products.map { it.toDomain() }
        }

    override suspend fun findById(id: String): Product? =
        dao.findById(id)?.takeUnless { it.isDeleted }?.toDomain()

    override suspend fun save(product: Product) {
        dao.upsert(
            product.copy(
                updatedAt = System.currentTimeMillis()
            ).toEntity()
        )
    }

    override suspend fun delete(id: String) {
        dao.markDeleted(id)
    }

    override suspend fun syncNow(): SyncReport {
        // 1. Obtener el usuario autenticado.
        val uid = authManager.signInAnonymously()

        // 2. Enviar los productos pendientes a Firestore.
        val pendingProducts = dao.getPending()
        var uploaded = 0

        pendingProducts.forEach { product ->
            remote.push(uid, product.toDto())
            dao.upsert(product.copy(pending = false))
            uploaded++
        }

        // 3. Descargar los productos guardados en Firestore.
        val remoteProducts = remote.fetchFromServer(uid)

        // 4. Guardarlos en Room.
        dao.upsertAll(remoteProducts.map { it.remoteToEntity() })

        return SyncReport(
            uploaded = uploaded,
            downloaded = remoteProducts.size
        )
    }
}