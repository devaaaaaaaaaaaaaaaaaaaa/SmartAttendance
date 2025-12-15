package com.example.smartattendance.data

import android.util.Log
import com.example.smartattendance.data.local.AttendanceDao
import com.example.smartattendance.data.model.AttendanceRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AttendanceRepository(
    private val dao: AttendanceDao
) {

    fun getHistory(userId: String) =
        dao.getHistory(userId)

    // 🔥 INI YANG KURANG
    suspend fun saveLocal(record: AttendanceRecord) {
        dao.insert(record)
    }

    suspend fun deleteLocalHistory(userId: String) {
        dao.deleteLocalHistory(userId)
    }

    suspend fun syncUnsynced(userId: String) {
        val unsynced = dao.getUnsynced()

        for (record in unsynced) {
            try {
                FirebaseFirestore.getInstance()
                    .collection("attendance")
                    .document(userId)
                    .collection("records")
                    .add(
                        mapOf(
                            "timestamp" to record.timestamp,
                            "latitude" to record.latitude,
                            "longitude" to record.longitude,
                            "photoPath" to record.photoPath
                        )
                    )
                    .await() // 🔥 WAJIB

                dao.markSynced(record.id)
            } catch (e: Exception) {
                Log.e("SYNC", "Gagal sync", e)
            }
        }
    }

}

