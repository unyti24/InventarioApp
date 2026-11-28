package com.sena.inventario_app.data.remote

data class ProductDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val priceCop: Long = 0L,
    val quantity: Int = 0,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)