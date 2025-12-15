package com.example.smartattendance.data.remote.dto

import com.example.smartattendance.data.model.User

data class LoginResponse(
    val token: String,
    val user: User
)
