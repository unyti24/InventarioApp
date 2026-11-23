package com.sena.inventario_app.data.mapper

import com.sena.inventario_app.data.local.ProductEntity
import com.sena.inventario_app.domain.model.Product

fun ProductEntity.toDomain() = Product(
    id, name, description, priceCop, quantity, updatedAt
)

fun Product.toEntity() = ProductEntity(
    id = id,
    name = name,
    description = description,
    priceCop = priceCop,
    quantity = quantity,
    updatedAt = updatedAt,
    pending = true,
    isDeleted = false
)