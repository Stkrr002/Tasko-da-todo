package com.alpharays.tasko_da_todo

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alpharays.tasko_da_todo.data.entity.Task
import com.alpharays.tasko_da_todo.presentation.featuretodolist.TaskViewModel
import com.alpharays.tasko_da_todo.presentation.dragdrop.DragDropColumn
import com.alpharays.tasko_da_todo.presentation.featuretodolist.dialog.EditTaskDialog
import com.alpharays.tasko_da_todo.ui.theme.TaskodatodoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random


data class DialogTaskData(
    val task: Task,
    val onSubmitClicked: (Task) -> Unit,
)


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskodatodoTheme {
                val viewModel: TaskViewModel = hiltViewModel()
                val itemsStateFlow = viewModel.tasks

                var showDialog by rememberSaveable { mutableStateOf(false) }


                fun swapItems(from: Int, to: Int) {
                    viewModel.reorderTasks(from, to)
                }

                fun onEditTask(task: Task) {
                    viewModel.editTask(task)
                    showDialog = false
                }

                fun onAddTask(task: Task) {
                    viewModel.addTask(task)
                    showDialog = false
                }

                fun onDeleteTask(task: Task) {
                    viewModel.deleteTask(task)
                }

                fun onItemClicked(clickedItem: Task) {
                    viewModel.showDialogData = DialogTaskData(
                        task = clickedItem,
                        onSubmitClicked = ::onEditTask,
                    )
                    showDialog = true
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DragNDropItemsList(
                            itemsStateFlow = itemsStateFlow,
                            onItemClicked = ::onItemClicked,
                            onSwapItems = ::swapItems,
                            onDelete = ::onDeleteTask,

                            )
                    }

                    FloatingActionButton(
                        onClick = {
                            viewModel.showDialogData = DialogTaskData(
                                Task(0, "", "", position = 0),
                                ::onAddTask,
                            )
                            showDialog = true
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Task"
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
        }
    }
}


@Composable
fun DragNDropItemsList(
    itemsStateFlow: StateFlow<List<Task>>,
    onItemClicked: (Task) -> Unit = {},
    onSwapItems: (Int, Int) -> Unit,
    onDelete: (Task) -> Unit
) {
    DragDropColumn(
        items = itemsStateFlow.collectAsState().value,
        onSwap = onSwapItems
    ) { item ->
        val randomColor = Color(
            red = Random(item.id).nextInt(256),
            green = Random(item.id + 1).nextInt(256),
            blue = Random(item.id + 2).nextInt(256)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked(item) },
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = randomColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title.replaceFirst(
                            oldChar = item.title.first(),
                            newChar = item.title.first().uppercaseChar()
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.description.replaceFirst(
                            oldChar = item.description.first(),
                            newChar = item.description.first().uppercaseChar()
                        ),
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                }
                IconButton(
                    onClick = { onDelete(item) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        }
    }
}
