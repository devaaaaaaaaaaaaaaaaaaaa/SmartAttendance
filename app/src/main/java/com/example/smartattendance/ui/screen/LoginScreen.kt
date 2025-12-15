package com.example.smartattendance.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.smartattendance.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoggedIn: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observasi status login Firebase
    val isLoggedIn = viewModel.isLoggedIn

    // Jika login berhasil → navigasi
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLoggedIn()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Smart Attendance", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
                if (viewModel.errorMessage != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = viewModel.errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
