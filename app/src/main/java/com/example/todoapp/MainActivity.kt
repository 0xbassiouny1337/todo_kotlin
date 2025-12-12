package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.screens.AddEditScreen
import com.example.todoapp.ui.screens.DetailScreen
import com.example.todoapp.ui.screens.MainScreen
import com.example.todoapp.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "main"
                    ) {

                        composable("main") {
                            MainScreen(
                                onNavigate = { route -> navController.navigate(route) }
                            )
                        }

                        composable("add") {
                            AddEditScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable("edit/{taskId}") { backStackEntry ->
                            val id = backStackEntry.arguments
                                ?.getString("taskId")
                                ?.toLongOrNull() ?: 0L

                            AddEditScreen(
                                taskId = id,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable("details/{taskId}") { backStackEntry ->
                            val id = backStackEntry.arguments
                                ?.getString("taskId")
                                ?.toLongOrNull() ?: 0L

                            DetailScreen(
                                taskId = id,
                                onNavigate = { route -> navController.navigate(route) }
                            )
                        }
                    }
                }
            }
        }
    }
}
