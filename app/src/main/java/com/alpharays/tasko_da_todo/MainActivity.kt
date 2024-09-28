package com.alpharays.tasko_da_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alpharays.tasko_da_todo.presentation.TaskViewModel
import com.alpharays.tasko_da_todo.ui.theme.TaskodatodoTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.alpharays.tasko_da_todo.data.entity.Task
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskodatodoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoListApp(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ToDoListApp(innerPadding: PaddingValues) {
    val viewModel: TaskViewModel = hiltViewModel()

    // Use ReorderableTaskList to display the tasks
    ReorderableTaskList(viewModel = viewModel)
}

@Composable
fun ReorderableTaskList(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var draggedItemIndex by remember { mutableStateOf<Int?>(null) }
    var offsetY by remember { mutableStateOf(0f) }

    LazyColumn {
        itemsIndexed(tasks, key = { _, task -> task.id }) { index, task ->
            TaskItem(
                task = task,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = if (draggedItemIndex == index) offsetY.dp else 0.dp)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                draggedItemIndex = index
                            },
                            onDragEnd = {
                                draggedItemIndex = null
                                offsetY = 0f
                            },
                            onDragCancel = {
                                draggedItemIndex = null
                                offsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetY += dragAmount.y
                                val newIndex = (index + (offsetY / 100).toInt()).coerceIn(0, tasks.size - 1)
                                if (newIndex != index) {
                                    coroutineScope.launch {
                                        viewModel.reorderTasks(index, newIndex)
                                    }
                                    draggedItemIndex = newIndex
                                    offsetY = 0f
                                }
                            }
                        )
                    },
                onEdit = { /* Handle edit */ },
                onDelete = { viewModel.deleteTask(it) }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onEdit: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray)
            .clickable { onEdit(task) }
    ) {
        Text(text = task.title, modifier = Modifier.weight(1f))
        IconButton(onClick = { onDelete(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
        }
    }
}