package com.example.todoapp.analytics

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import androidx.activity.ComponentActivity

class AnalyticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val completed = intent.getIntExtra("completed", 0)
        val pending = intent.getIntExtra("pending", 0)

        // Launch FlutterActivity with initial route
        val flutterIntent = FlutterActivity
            .withNewEngine()
            .initialRoute("/analytics?completed=$completed&pending=$pending")
            .build(this)

        startActivity(flutterIntent)
        finish() // optional: close this Kotlin activity
    }
}
