package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.model.ToDoItem
import com.example.todoapp.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    taskId: Long? = null,
    onNavigateBack: () -> Unit,
    viewModel: ToDoViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(0) }

    // load existing task if editing
    LaunchedEffect(taskId) {
        if (taskId != null && taskId != 0L) {
            val t = viewModel.getTaskById(taskId)
            t?.let {
                title = it.title
                description = it.description ?: ""
                priority = it.priority
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(if (taskId == null || taskId == 0L) "Add Task" else "Edit Task")
            })
        }
    ) { contentPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Priority", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ElevatedButton(onClick = { priority = 0 }) { Text("Low") }
                ElevatedButton(onClick = { priority = 1 }) { Text("Medium") }
                ElevatedButton(onClick = { priority = 2 }) { Text("High") }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    if (title.isBlank()) return@Button
                    scope.launch {
                        val item = ToDoItem(
                            id = if (taskId == null) 0L else taskId,
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            priority = priority
                        )
                        if (taskId == null || taskId == 0L) viewModel.addTask(item)
                        else viewModel.updateTask(item)
                        onNavigateBack()
                    }
                }) {
                    Text("Save")
                }

                OutlinedButton(onClick = onNavigateBack) {
                    Text("Cancel")
                }
            }
        }
    }
}
