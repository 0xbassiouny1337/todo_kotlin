package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.model.ToDoItem
import com.example.todoapp.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    taskId: Long,
    onNavigate: (String) -> Unit,
    viewModel: ToDoViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var item by remember { mutableStateOf<ToDoItem?>(null) }

    // Load task once when taskId changes
    LaunchedEffect(taskId) {
        item = viewModel.getTaskById(taskId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(item?.title ?: "Details")
            })
        }
    ) { contentPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)) {

            item?.let { t ->
                Text(text = "Title:", style = MaterialTheme.typography.titleMedium)
                Text(text = t.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Description:", style = MaterialTheme.typography.titleMedium)
                Text(text = t.description ?: "-", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Priority: ${when (t.priority) {
                    0 -> "Low"
                    1 -> "Medium"
                    else -> "High"
                }}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Completed: ${if (t.isCompleted) "Yes" else "No"}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onNavigate("edit/${t.id}") }) {
                        Text("Edit")
                    }

                    Button(onClick = {
                        scope.launch {
                            viewModel.toggleComplete(t)
                            item = viewModel.getTaskById(taskId)
                        }
                    }) {
                        Text(if (t.isCompleted) "Mark Pending" else "Mark Completed")
                    }

                    OutlinedButton(onClick = {
                        scope.launch {
                            viewModel.deleteTask(t)
                            onNavigate("main")
                        }
                    }) {
                        Text("Delete")
                    }
                }
            } ?: run {
                // loading / empty state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
