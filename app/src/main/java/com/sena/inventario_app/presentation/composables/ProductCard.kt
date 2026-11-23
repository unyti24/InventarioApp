package com.sena.inventario_app.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.inventario_app.domain.model.Product
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductCard(
    product: Product,
    onEdit: () -> Unit,
    onAskDelete: () -> Unit
) {
    val format = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"))
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = product.description)

            Text(
                text = "${format.format(product.priceCop)} | Cantidad: ${product.quantity}"
            )

            Row {
                TextButton(onClick = onEdit) {
                    Text("Editar")
                }

                TextButton(onClick = onAskDelete) {
                    Text("Eliminar")
                }
            }
        }
    }
}