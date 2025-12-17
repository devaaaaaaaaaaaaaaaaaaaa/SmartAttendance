# Smart Attendance App

Aplikasi presensi berbasis Android dengan konsep offline-first.

## Fitur
- Login Firebase Authentication
- Presensi selfie + lokasi
- Penyimpanan lokal (Room)
- Sinkronisasi otomatis ke Firebase Firestore
- Riwayat presensi

## Teknologi
- Android Studio
- Kotlin Jetpack Compose (UI modern)
- Jetpack Navigation (navigasi antar layar)
- CameraX (foto selfie saat presensi)
- Location Based Service (LBS) (cek koordinat presensi)
- Room Database (data offline)
- Firebase Authentication (login)
- Firebase Firestore (sinkronisasi presensi)

## Konsep
Offline-first: data presensi tetap tersimpan meskipun jaringan tidak stabil.

## How to Run
1. Clone repository
2. Tambahkan `google-services.json` milik Firebase sendiri
3. Sync Gradle
4. Run di Android Studio
