package com.sena.inventario_app.presentation.state

data class ProductFormUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val quantity: String = "",
    val saving: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
)