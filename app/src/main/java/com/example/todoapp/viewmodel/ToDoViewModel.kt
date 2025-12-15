package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.ToDoDatabase
import com.example.todoapp.data.model.ToDoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

enum class Filter { ALL, COMPLETED, PENDING }

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ToDoDatabase.getDatabase(application).toDoDao()

    private val _filter = MutableStateFlow(Filter.ALL)

    // Expose tasks as StateFlow according to selected filter
    val tasks: StateFlow<List<ToDoItem>> = _filter.flatMapLatest { filter ->
        when (filter) {
            Filter.ALL -> dao.getAll()
            Filter.COMPLETED -> dao.getByStatus(true)
            Filter.PENDING -> dao.getByStatus(false)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allTasks: StateFlow<List<ToDoItem>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun setFilter(filter: Filter) {
        _filter.value = filter
    }

    fun addTask(item: ToDoItem) = viewModelScope.launch {
        dao.insert(item)
    }

    fun updateTask(item: ToDoItem) = viewModelScope.launch {
        dao.update(item)
    }

    fun deleteTask(item: ToDoItem) = viewModelScope.launch {
        dao.delete(item)
    }

    fun deleteById(id: Long) = viewModelScope.launch {
        dao.deleteById(id)
    }

    fun toggleComplete(item: ToDoItem) = viewModelScope.launch {
        val updated = item.copy(isCompleted = !item.isCompleted)
        dao.update(updated)
    }

    suspend fun getTaskById(id: Long): ToDoItem? = dao.getById(id)
}
