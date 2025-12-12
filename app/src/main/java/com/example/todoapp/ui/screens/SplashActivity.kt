package com.example.todoapp.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.draw.alpha


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreenUI(
                onFinished = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            )
        }
    }
}

@Composable
fun SplashScreenUI(onFinished: () -> Unit) {
    val scope = rememberCoroutineScope()

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1200)
    )

    // Trigger animations + navigate
    LaunchedEffect(true) {
        startAnimation = true
        delay(1800)
        onFinished()
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F9D58),
                        Color(0xFF0A7F42)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Logo circle
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnim.value)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.7f)
                            )
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📝",
                    fontSize = 48.sp,
                    modifier = Modifier.scale(scaleAnim.value)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App name
            Text(
                text = "ToDo App",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            )
        }
    }
}
