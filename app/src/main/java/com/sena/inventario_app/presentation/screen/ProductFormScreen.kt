package com.sena.inventario_app.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sena.inventario_app.presentation.section.ProductFormSection
import com.sena.inventario_app.presentation.viewmodel.ProductFormViewModel

@Composable
fun ProductFormScreen(
    productId: String?,
    onBack: () -> Unit,
    viewModel: ProductFormViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.setupForm(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (productId == null) "Registrar producto" else "Editar producto"
        )

        ProductFormSection(
            productId = productId,
            viewModel = viewModel,
            onSuccess = onBack
        )

        Button(onClick = onBack) {
            Text("Cancelar")
        }
    }
}