package com.example.todoapp.analytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AnalyticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val completed = intent.getIntExtra("completed", 0)
        val pending = intent.getIntExtra("pending", 0)

        setContent {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "Analytics", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Completed tasks: $completed")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Pending tasks: $pending")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "This screen is implemented in Kotlin. If you want Flutter here, replace activity with FlutterActivity.")
            }
        }
    }
}
