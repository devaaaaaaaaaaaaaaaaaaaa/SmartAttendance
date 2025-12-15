package com.example.smartattendance.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartattendance.data.AttendanceRepository
import com.example.smartattendance.data.local.DatabaseModule

class AttendanceViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttendanceViewModel::class.java)) {
            val db = DatabaseModule.provideDatabase(context)
            val repo = AttendanceRepository(db.attendanceDao())
            @Suppress("UNCHECKED_CAST")
            return AttendanceViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
