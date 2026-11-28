package com.sena.inventario_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.inventario_app.domain.repository.ProductRepository
import com.sena.inventario_app.presentation.state.ProductListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _ui = MutableStateFlow(ProductListUiState())
    val ui: StateFlow<ProductListUiState> = _ui

    init {
        viewModelScope.launch {
            repository.observeProducts()
                .catch { error ->
                    _ui.value = _ui.value.copy(
                        message = error.message ?: "Error al cargar productos"
                    )
                }
                .collect { products ->
                    _ui.value = _ui.value.copy(products = products)
                }
        }

        sync()
    }

    fun sync() = viewModelScope.launch {
        _ui.value = _ui.value.copy(
            isSyncing = true,
            message = null
        )

        try {
            val report = repository.syncNow()

            _ui.value = _ui.value.copy(
                message = "Sincronizado: ${report.uploaded} enviados"
            )
        } catch (e: Exception) {
            _ui.value = _ui.value.copy(
                message = "Sin conexión: datos locales disponibles"
            )
        } finally {
            _ui.value = _ui.value.copy(isSyncing = false)
        }
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.delete(id)
    }
}