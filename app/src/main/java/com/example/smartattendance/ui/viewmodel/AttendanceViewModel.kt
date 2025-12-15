package com.example.smartattendance.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartattendance.data.AttendanceRepository
import com.example.smartattendance.data.model.AttendanceRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AttendanceViewModel(
    private val repo: AttendanceRepository
) : ViewModel() {

    fun history(userId: String) =
        repo.getHistory(userId)

    fun deleteLocalHistory(userId: String) {
        viewModelScope.launch {
            repo.deleteLocalHistory(userId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAttendance(
        userId: String,
        latitude: Double,
        longitude: Double,
        photoPath: String
    ) {
        viewModelScope.launch {
            repo.saveLocal(
                AttendanceRecord(
                    userId = userId,
                    timestamp = LocalDateTime.now().toString(),
                    latitude = latitude,
                    longitude = longitude,
                    photoPath = photoPath,
                    synced = false
                )
            )
        }
    }

    fun sync(userId: String) {
        viewModelScope.launch {
            repo.syncUnsynced(userId)
        }
    }
}
