package com.sena.inventario_app.domain.usecase

import com.sena.inventario_app.domain.model.Product
import com.sena.inventario_app.domain.repository.ProductRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        require(product.name.isNotBlank()) { "Nombre obligatorio" }
        require(product.name.length <= 80) { "Máximo 80 caracteres" }
        require(product.description.length <= 500)
        require(product.priceCop > 0) { "El precio debe ser positivo" }
        require(product.quantity >= 0) { "Cantidad inválida" }

        repository.save(product)
    }
}