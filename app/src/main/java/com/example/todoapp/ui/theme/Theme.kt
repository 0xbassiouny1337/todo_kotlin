package com.example.todoapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF1EB980),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF045D56)
)

@Composable
fun TodoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        content = content
    )
}
