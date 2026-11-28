package com.sena.inventario_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.inventario_app.domain.model.Product
import com.sena.inventario_app.domain.repository.ProductRepository
import com.sena.inventario_app.domain.usecase.SaveProductUseCase
import com.sena.inventario_app.presentation.state.ProductFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val saveProductUseCase: SaveProductUseCase
) : ViewModel() {

    private val _ui = kotlinx.coroutines.flow.MutableStateFlow(ProductFormUiState())
    val ui = _ui

    fun updateName(value: String) {
        _ui.value = _ui.value.copy(name = value)
    }

    fun updateDescription(value: String) {
        _ui.value = _ui.value.copy(description = value)
    }

    fun updatePrice(value: String) {
        _ui.value = _ui.value.copy(price = value)
    }

    fun updateQuantity(value: String) {
        _ui.value = _ui.value.copy(quantity = value)
    }

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            currentProductId = productId

            val product = repository.findById(productId)

            if (product != null) {
                _ui.value = _ui.value.copy(
                    name = product.name,
                    description = product.description,
                    price = product.priceCop.toString(),
                    quantity = product.quantity.toString()
                )
            }
        }
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _ui.value = _ui.value.copy(
                saving = true,
                error = null
            )

            try {
                val price = _ui.value.price.toLongOrNull()
                val quantity = _ui.value.quantity.toIntOrNull()

                if (price == null || quantity == null) {
                    _ui.value = _ui.value.copy(
                        error = "El precio y la cantidad deben ser números válidos",
                        saving = false
                    )
                    return@launch
                }

                val product = Product(
                    id = currentProductId ?: java.util.UUID.randomUUID().toString(),
                    name = _ui.value.name.trim(),
                    description = _ui.value.description.trim(),
                    priceCop = price,
                    quantity = quantity
                )

                saveProductUseCase(product)

                _ui.value = _ui.value.copy(
                    saving = false,
                    saved = true
                )

                onSuccess()

            } catch (e: Exception) {
                _ui.value = _ui.value.copy(
                    error = e.message ?: "Error al guardar el producto",
                    saving = false
                )
            }
        }
    }

    private var currentProductId: String? = null
}