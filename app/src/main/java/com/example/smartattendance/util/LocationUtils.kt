package com.example.smartattendance.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class SimpleLocation(
    val latitude: Double,
    val longitude: Double
)

object LocationUtils {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): SimpleLocation? =
        suspendCancellableCoroutine { cont ->
            val client = LocationServices.getFusedLocationProviderClient(context)
            client.lastLocation
                .addOnSuccessListener { loc ->
                    if (loc != null) {
                        cont.resume(SimpleLocation(loc.latitude, loc.longitude))
                    } else {
                        cont.resume(null)
                    }
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
}
