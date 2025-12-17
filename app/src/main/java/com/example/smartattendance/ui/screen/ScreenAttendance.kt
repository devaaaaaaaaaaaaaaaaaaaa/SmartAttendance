package com.example.smartattendance.ui.screen

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.smartattendance.ui.camera.CameraCapture
import com.example.smartattendance.ui.viewmodel.AttendanceViewModel
import com.example.smartattendance.util.LocationUtils
import com.example.smartattendance.util.NetworkObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AttendanceScreen(
    userId: String,
    viewModel: AttendanceViewModel,
    onOpenHistory: () -> Unit
) {
    val context = LocalContext.current

    // Permission
    val locationPermission =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPermission =
        rememberPermissionState(Manifest.permission.CAMERA)

    // State
    var photoFile by remember { mutableStateOf<File?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Network observer
    val networkObserver = remember { NetworkObserver(context) }
    val isConnected by networkObserver.isConnected.collectAsState()

    // Output directory
    val outputDir = remember {
        context.getExternalFilesDir("photos") ?: context.filesDir
    }

    // Request permission once
    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
        networkObserver.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            networkObserver.stop()
        }
    }

    // Presensi setelah foto diambil
    LaunchedEffect(photoFile) {
        photoFile?.let { file ->
            val loc = LocationUtils.getCurrentLocation(context)
            if (loc != null) {

                viewModel.saveAttendanceSuspend(
                    userId = userId,
                    latitude = loc.latitude,
                    longitude = loc.longitude,
                    photoPath = file.absolutePath
                )

                viewModel.sync(userId)

                val message = if (isConnected)
                    "Presensi tersimpan & disinkronkan"
                else
                    "Presensi tersimpan (offline)"

                snackbarHostState.showSnackbar(message)
            }
        }
    }


    // AUTO SYNC saat internet kembali
    LaunchedEffect(isConnected) {
        if (isConnected) {0
            snackbarHostState.showSnackbar(
                message = "Koneksi tersedia, sinkronisasi data..."
            )
            viewModel.sync(userId)
        }
    }

    // Kamera controller
    var takePhotoAction by remember {
        mutableStateOf<(() -> Unit)?>(null)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Presensi Hari Ini",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (isConnected) "Status: Online" else "Status: Offline",
                color = if (isConnected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                CameraCapture(
                    modifier = Modifier.fillMaxSize(),
                    outputDirectory = outputDir,
                    onControllerReady = { action ->
                        takePhotoAction = action
                    }
                ) { file ->
                    photoFile = file
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    takePhotoAction?.invoke()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ambil Selfie & Presensi")
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = onOpenHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lihat Riwayat Presensi")
            }
        }
    }
}

