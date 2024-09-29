package com.alpharays.tasko_da_todo.presentation.featuretodolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alpharays.tasko_da_todo.DialogTaskData
import com.alpharays.tasko_da_todo.domain.model.TaskData
import com.alpharays.tasko_da_todo.presentation.featuretodolist.components.TodoItemList
import com.alpharays.tasko_da_todo.presentation.featuretodolist.dialog.EditTaskDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(){
    val viewModel: TaskViewModel = hiltViewModel()
    val itemsStateFlow = viewModel.tasks

    var showDialog by rememberSaveable { mutableStateOf(false) }

    fun swapItems(from: Int, to: Int) {
        viewModel.reorderTasks(from, to)
    }

    fun onEditTask(task: TaskData) {
        viewModel.editTask(task)
        showDialog = false
    }

    fun onAddTask(task: TaskData) {
        viewModel.addTask(task)
        showDialog = false
    }

    fun onDeleteTask(task: TaskData) {
        viewModel.deleteTask(task)
    }

    fun onItemClicked(clickedItem: TaskData) {
        viewModel.showDialogData = DialogTaskData(
            task = clickedItem,
            onSubmitClicked = ::onEditTask,
        )
        showDialog = true
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasko-da-todo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.showDialogData = DialogTaskData(
                        TaskData(0, "", "", position = 0),
                        ::onAddTask,
                    )
                    showDialog = true
                },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoItemList(
                        itemsStateFlow = itemsStateFlow,
                        onItemClicked = ::onItemClicked,
                        onSwapItems = ::swapItems,
                        onDelete = ::onDeleteTask
                    )
                }

                if (showDialog && viewModel.showDialogData != null) {
                    EditTaskDialog(
                        task = viewModel.showDialogData!!.task,
                        onSubmitClicked = viewModel.showDialogData!!.onSubmitClicked
                    ) {
                        showDialog = false
                    }
                }
            }
        }
    )
}