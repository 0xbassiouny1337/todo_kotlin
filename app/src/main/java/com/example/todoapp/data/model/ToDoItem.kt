package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String? = null,
    val dueAt: Long? = null, // store epoch millis (optional)
    val priority: Int = 0, // 0 = low, 1 = med, 2 = high
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)
