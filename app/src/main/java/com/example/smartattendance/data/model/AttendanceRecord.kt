package com.example.smartattendance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "attendance")
data class AttendanceRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: String,
    val timestamp: String,
    val latitude: Double,
    val longitude: Double,
    val photoPath: String,

    val synced: Boolean = false
)


