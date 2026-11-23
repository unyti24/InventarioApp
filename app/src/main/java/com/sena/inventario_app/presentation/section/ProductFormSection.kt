package com.sena.inventario_app.presentation.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sena.inventario_app.presentation.composables.ProductFormField
import com.sena.inventario_app.presentation.viewmodel.ProductFormViewModel

@Composable
fun ProductFormSection(
    productId: String?,
    viewModel: ProductFormViewModel,
    onSuccess: () -> Unit
) {
    val ui by viewModel.ui.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductFormField(
            label = "Nombre",
            value = ui.name,
            onValueChange = viewModel::updateName
        )

        ProductFormField(
            label = "Descripción",
            value = ui.description,
            onValueChange = viewModel::updateDescription
        )

        ProductFormField(
            label = "Precio",
            value = ui.price,
            onValueChange = viewModel::updatePrice,
            keyboardType = KeyboardType.Number
        )

        ProductFormField(
            label = "Cantidad",
            value = ui.quantity,
            onValueChange = viewModel::updateQuantity,
            keyboardType = KeyboardType.Number
        )

        ui.error?.let { error ->
            Text(text = error)
        }

        Button(
            onClick = {
                viewModel.save(onSuccess)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (productId == null) {
                    "Guardar producto"
                } else {
                    "Actualizar producto"
                }
            )
        }
    }
}