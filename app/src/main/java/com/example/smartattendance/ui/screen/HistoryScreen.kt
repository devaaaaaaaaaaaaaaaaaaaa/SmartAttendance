package com.example.smartattendance.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.smartattendance.data.model.AttendanceRecord
import com.example.smartattendance.ui.viewmodel.AttendanceViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    userId: String,
    viewModel: AttendanceViewModel
) {
    val history by viewModel
        .history(userId)
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Presensi") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // ✅ TOMBOL HAPUS (SEKARANG AKAN MUNCUL)
            Button(
                onClick = { viewModel.deleteLocalHistory(userId) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Hapus Riwayat (Lokal)")
            }

            Spacer(Modifier.height(16.dp))

            if (history.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada data presensi")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(history) { record ->
                        AttendanceItem(record)
                    }
                }
            }
        }
    }
}


@Composable
fun AttendanceItem(record: AttendanceRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(File(record.photoPath)),
                contentDescription = "Foto Presensi",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = record.timestamp,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Lat: ${record.latitude}, Lon: ${record.longitude}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = if (record.synced)
                    "☁️ Tersinkron ke Firebase"
                else
                    "📱 Tersimpan lokal",
                color = if (record.synced)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}