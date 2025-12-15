package com.example.smartattendance.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartattendance.data.model.AttendanceRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert
    suspend fun insert(record: AttendanceRecord)

    @Query("SELECT * FROM attendance WHERE userId = :userId ORDER BY timestamp DESC")
    fun getHistory(userId: String): Flow<List<AttendanceRecord>>

    @Query("SELECT * FROM attendance WHERE synced = 0")
    suspend fun getUnsynced(): List<AttendanceRecord>

    @Query("UPDATE attendance SET synced = 1 WHERE id = :id")
    suspend fun markSynced(id: Long)

    @Query("DELETE FROM attendance WHERE userId = :userId")
    suspend fun deleteLocalHistory(userId: String)
}


