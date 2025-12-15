package com.example.smartattendance.data.remote.dto

data class AttendanceRequest(
    val userId: String,
    val timestamp: String,
    val latitude: Double,
    val longitude: Double,
    val photoBase64: String // foto selfie dikirim sebagai base64
)
