package com.example.smartattendance.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartattendance.data.model.User
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.getValue

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val token: String? = null,
    val error: String? = null
)

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var isLoggedIn by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String) {
        errorMessage = null

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoggedIn = true
            }
            .addOnFailureListener { e ->
                errorMessage = e.message
            }
    }

    fun currentUserId(): String? = auth.currentUser?.uid
}
