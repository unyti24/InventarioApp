package com.sena.inventario_app.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sena.inventario_app.domain.model.Product
import com.sena.inventario_app.presentation.composables.ProductDeleteDialog
import com.sena.inventario_app.presentation.section.ProductListSection
import com.sena.inventario_app.presentation.viewmodel.ProductListViewModel

@Composable
fun ProductListScreen(
    onNavigateToForm: (String?) -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val ui by viewModel.ui.collectAsState()
    var productToDelete by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Inventario")

        Button(
            onClick = { onNavigateToForm(null) }
        ) {
            Text("Agregar producto")
        }

        Button(
            onClick = { viewModel.sync() }
        ) {
            Text("Sincronizar")
        }

        if (ui.isSyncing) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        ui.message?.let {
            Text(it)
        }

        ProductListSection(
            products = ui.products,
            onEdit = { product ->
                onNavigateToForm(product.id)
            },
            onDelete = { product ->
                productToDelete = product
            },
            modifier = Modifier.weight(1f)
        )
    }

    productToDelete?.let { product ->
        ProductDeleteDialog(
            onConfirm = {
                viewModel.delete(product.id)
                productToDelete = null
            },
            onDismiss = {
                productToDelete = null
            }
        )
    }
}