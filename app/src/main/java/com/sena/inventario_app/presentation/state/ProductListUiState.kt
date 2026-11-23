package com.sena.inventario_app.presentation.state

import com.sena.inventario_app.domain.model.Product

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isSyncing: Boolean = false,
    val message: String? = null
)