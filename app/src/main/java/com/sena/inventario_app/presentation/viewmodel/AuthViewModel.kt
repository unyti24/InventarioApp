package com.sena.inventario_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.inventario_app.data.remote.FirebaseAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager
) : ViewModel() {

    private val _uid = MutableStateFlow<String?>(null)
    val uid: StateFlow<String?> = _uid

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        authenticate()
    }

    private fun authenticate() {
        viewModelScope.launch {
            try {
                _uid.value = authManager.signInAnonymously()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error de autenticación"
            }
        }
    }
}