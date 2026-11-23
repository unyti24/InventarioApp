package com.sena.inventario_app.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.entryProvider
import kotlinx.serialization.Serializable

@Serializable
data object ProductListRoute : NavKey

@Serializable
data class ProductFormRoute(val productId: String? = null) : NavKey

@Composable
fun AppNavigation() {

    val backStack = rememberNavBackStack(ProductListRoute)

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<ProductListRoute> {
                ProductListScreen(
                    onNavigateToForm = { productId ->
                        backStack.add(ProductFormRoute(productId))
                    }
                )
            }

            entry<ProductFormRoute> { route ->
                ProductFormScreen(
                    productId = route.productId,
                    onBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}