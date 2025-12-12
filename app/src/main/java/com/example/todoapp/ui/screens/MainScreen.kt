package com.example.todoapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.analytics.AnalyticsActivity
import com.example.todoapp.data.model.ToDoItem
import com.example.todoapp.viewmodel.Filter
import com.example.todoapp.viewmodel.ToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate: (String) -> Unit
) {
    val vm: ToDoViewModel = viewModel()
    val tasks by vm.tasks.collectAsState()
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf(Filter.ALL) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("To-Do List") },
                actions = {
                    IconButton(onClick = {
                        val completedCount = tasks.count { it.isCompleted }
                        val pendingCount = tasks.size - completedCount
                        val intent = Intent(context, AnalyticsActivity::class.java).apply {
                            putExtra("completed", completedCount)
                            putExtra("pending", pendingCount)
                        }
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Filled.Assessment, contentDescription = "Analytics")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate("add") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            FilterRow(selected = selectedFilter, onSelect = {
                selectedFilter = it
                vm.setFilter(it)
            })

            TaskList(
                tasks = tasks,
                onClick = { item -> onNavigate("details/${item.id}") },
                onToggle = { vm.toggleComplete(it) }
            )
        }
    }
}

@Composable
private fun FilterRow(selected: Filter, onSelect: (Filter) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilterChip("All", selected == Filter.ALL) { onSelect(Filter.ALL) }
        FilterChip("Pending", selected == Filter.PENDING) { onSelect(Filter.PENDING) }
        FilterChip("Completed", selected == Filter.COMPLETED) { onSelect(Filter.COMPLETED) }
    }
}

@Composable
private fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        tonalElevation = if (selected) 4.dp else 0.dp,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun TaskList(
    tasks: List<ToDoItem>,
    onClick: (ToDoItem) -> Unit,
    onToggle: (ToDoItem) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tasks) { item ->
            TaskRow(item = item, onClick = { onClick(item) }, onToggle = { onToggle(item) })
        }
    }
}

@Composable
private fun TaskRow(
    item: ToDoItem,
    onClick: () -> Unit,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle() })
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)
            item.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }
        }
        Text(
            text = when (item.priority) {
                0 -> "L"
                1 -> "M"
                else -> "H"
            }
        )
    }
}
