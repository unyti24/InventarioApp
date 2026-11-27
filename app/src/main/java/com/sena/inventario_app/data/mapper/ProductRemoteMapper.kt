package com.sena.inventario_app.data.mapper

import com.sena.inventario_app.data.local.ProductEntity
import com.sena.inventario_app.data.remote.ProductDto

fun ProductEntity.toDto(): ProductDto {
    return ProductDto(
        id = id,
        name = name,
        description = description,
        priceCop = priceCop,
        quantity = quantity,
        updatedAt = updatedAt,
        isDeleted = isDeleted
    )
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        description = description,
        priceCop = priceCop,
        quantity = quantity,
        updatedAt = updatedAt,
        pending = false,
        isDeleted = isDeleted
    )
}