package com.example.smartattendance

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartattendance.ui.nav.NavRoutes
import com.example.smartattendance.ui.screen.AttendanceScreen
import com.example.smartattendance.ui.screen.HistoryScreen
import com.example.smartattendance.ui.screen.LoginScreen
import com.example.smartattendance.ui.theme.SmartAttendanceTheme
import com.example.smartattendance.ui.viewmodel.AttendanceViewModel
import com.example.smartattendance.ui.viewmodel.AttendanceViewModelFactory
import com.example.smartattendance.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Capture the activity context here
        val activity = this

        setContent {
            SmartAttendanceTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()

                    // Use the captured context to create the ViewModel
                    val attendanceViewModel: AttendanceViewModel = viewModel(
                        factory = AttendanceViewModelFactory(activity)
                    )

                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.LOGIN
                    ) {
                        composable(NavRoutes.LOGIN) {
                            LoginScreen(
                                viewModel = authViewModel,
                                onLoggedIn = {
                                    navController.navigate(NavRoutes.ATTENDANCE) {
                                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(NavRoutes.ATTENDANCE) {
                            val userId = authViewModel.currentUserId()
                            if (userId != null) {
                                AttendanceScreen(
                                    userId = userId,
                                    viewModel = attendanceViewModel,
                                    onOpenHistory = {
                                        navController.navigate(NavRoutes.HISTORY)
                                    }
                                )
                            }
                        }

                        composable(NavRoutes.HISTORY) {
                            val userId = authViewModel.currentUserId()
                            if (userId != null) {
                                HistoryScreen(
                                    userId = userId,
                                    viewModel = attendanceViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
