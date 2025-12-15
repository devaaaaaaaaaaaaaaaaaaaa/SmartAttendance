package com.example.smartattendance.data.remote

import com.example.smartattendance.data.remote.dto.AttendanceRequest
import com.example.smartattendance.data.remote.dto.LoginRequest
import com.example.smartattendance.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @POST("attendance")
    suspend fun sendAttendance(
        @Header("Authorization") token: String,
        @Body body: AttendanceRequest
    )
}
