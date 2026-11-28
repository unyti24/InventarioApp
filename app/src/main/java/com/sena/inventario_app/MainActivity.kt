package com.sena.inventario_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.sena.inventario_app.presentation.screen.AppNavigation
import com.sena.inventario_app.presentation.viewmodel.AuthViewModel
import com.sena.inventario_app.ui.theme.Inventario_appTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Inventario_appTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthCheck()
                }
            }
        }
    }
}

@Composable
fun AuthCheck(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uid by viewModel.uid.collectAsState()
    val error by viewModel.error.collectAsState()

    if (uid != null) {
        AppNavigation()
    } else if (error != null) {
        androidx.compose.material3.Text(
            text = "Error de Firebase: $error"
        )
    } else {
        androidx.compose.material3.CircularProgressIndicator()
    }
}