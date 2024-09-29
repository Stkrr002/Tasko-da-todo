package com.alpharays.tasko_da_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
                            onSwapItems = ::swapItems
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

                    if (showDialog && viewModel.showDialogData!=null) {
                        EditTaskDialog(
                            task =  viewModel.showDialogData!!.task,
                            onSubmitClicked =  viewModel.showDialogData!!.onSubmitClicked
                        )
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
    onSwapItems: (Int, Int) -> Unit
) {
    DragDropColumn(
        items = itemsStateFlow.collectAsState().value,
        onSwap = onSwapItems
    ) { item ->
        Card(
            modifier = Modifier
                .clickable {
                    onItemClicked(item)
                },
        ) {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(Random(item.id).nextLong()))
                    .padding(16.dp),
            )
        }
    }
}



