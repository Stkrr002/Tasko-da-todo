package com.alpharays.tasko_da_todo.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alpharays.tasko_da_todo.domain.model.Task
import com.google.accompanist.reorderable.*


@Composable
fun ToDoListScreen(viewModel: ToDoListViewModel = hiltViewModel()) {
    val tasks = viewModel.tasks.collectAsState().value
    val reorderState = rememberReorderableState(onMove = { from, to ->
        viewModel.reorderTask(from.index, to.index)
    })

    LazyColumn(
        modifier = Modifier.reorderable(reorderState),
        state = reorderState.lazyListState
    ) {
        items(tasks, { it.id }) { task ->
            TaskItem(task, viewModel::deleteTask, viewModel::editTask)
        }
    }

    FloatingActionButton(
        onClick = { viewModel.addTask(Task("New Task")) },
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Task")
    }
}

@Composable
fun TaskItem(task: Task, onDelete: (Task) -> Unit, onEdit: (Task) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(task.name, modifier = Modifier.weight(1f))
        IconButton(onClick = { onEdit(task) }) { Icon(Icons.Default.Edit, contentDescription = "Edit") }
        IconButton(onClick = { onDelete(task) }) { Icon(Icons.Default.Delete, contentDescription = "Delete") }
    }
}
