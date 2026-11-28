package com.sena.inventario_app.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signInAnonymously(): String {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            return currentUser.uid
        }

        val result = firebaseAuth.signInAnonymously().await()
        return result.user?.uid
            ?: throw IllegalStateException("No se pudo autenticar al usuario")
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}