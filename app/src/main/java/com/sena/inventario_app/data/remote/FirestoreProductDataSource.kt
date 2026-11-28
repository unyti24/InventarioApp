package com.sena.inventario_app.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreProductDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {

    private fun products(uid: String) =
        db.collection("users")
            .document(uid)
            .collection("products")

    suspend fun push(uid: String, dto: ProductDto) {

        val data = mapOf(
            "id" to dto.id,
            "name" to dto.name,
            "description" to dto.description,
            "priceCop" to dto.priceCop,
            "quantity" to dto.quantity,
            "updatedAt" to dto.updatedAt,
            "isDeleted" to dto.isDeleted
        )

        products(uid)
            .document(dto.id)
            .set(data)
            .await()
    }

    suspend fun fetchFromServer(uid: String): List<ProductDto> {

        return products(uid)
            .get(Source.SERVER)
            .await()
            .documents
            .mapNotNull { document ->

                val id = document.getString("id")
                    ?: return@mapNotNull null

                ProductDto(
                    id = id,
                    name = document.getString("name").orEmpty(),
                    description = document.getString("description").orEmpty(),
                    priceCop = document.getLong("priceCop") ?: 0L,
                    quantity = (document.getLong("quantity") ?: 0L).toInt(),
                    updatedAt = document.getLong("updatedAt") ?: 0L,
                    isDeleted = document.getBoolean("isDeleted") ?: false
                )
            }
    }
}