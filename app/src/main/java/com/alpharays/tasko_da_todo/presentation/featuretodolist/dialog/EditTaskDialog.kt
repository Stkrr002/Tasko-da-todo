package com.alpharays.tasko_da_todo.presentation.featuretodolist.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.alpharays.tasko_da_todo.data.entity.Task

@Composable
fun EditTaskDialog(task: Task,onSubmitClicked: (Task) -> Unit){
    var taskTitle by remember { mutableStateOf(task.title) }
    var taskDescription by remember { mutableStateOf(task.description) }
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Add Task") },
        text = {
            Column {
                TextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskTitle.isNotBlank() && taskDescription.isNotBlank()) {
                        onSubmitClicked(task.copy(title = taskTitle, description = taskDescription))
                    }

                }
            ) {

                Text("Submit")
            }
        },
        dismissButton = {
            Button(onClick = {  }) {
                Text("Cancel")
            }
        }
    )
}