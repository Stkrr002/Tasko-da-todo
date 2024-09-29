package com.alpharays.tasko_da_todo.presentation.featuretodolist.dialog


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpharays.tasko_da_todo.domain.model.TaskData

@Composable
fun EditTaskDialog(task: TaskData, onSubmitClicked: (TaskData) -> Unit, onCancelClicked: () -> Unit) {
    var taskTitle by remember { mutableStateOf(task.title) }
    var taskDescription by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = { onCancelClicked() },
        title = { Text(text = "Add Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    )
                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onCancelClicked() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                val context = LocalContext.current

                Button(
                    onClick = {
                        if (taskTitle.isNotBlank() && taskDescription.isNotBlank()) {
                            onSubmitClicked(
                                task.copy(
                                    title = taskTitle,
                                    description = taskDescription
                                )
                            )
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
            }
        }
    )
}