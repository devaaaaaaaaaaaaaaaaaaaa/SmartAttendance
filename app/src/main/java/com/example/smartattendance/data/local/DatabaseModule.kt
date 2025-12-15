package com.example.smartattendance.data.local

import android.content.Context
import androidx.room.Room

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "smart_attendance.db"
        ).build()
}
