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
import com.alpharays.tasko_da_todo.presentation.dragdrop.DragDropColumn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskodatodoTheme {
                val viewModel: TaskViewModel = hiltViewModel()
                val itemsStateFlow =
                    viewModel._tasks

                fun onItemClicked(clickedItem: Task) {
                    itemsStateFlow.update { currentList ->
                        val newList = currentList.toMutableList()
                            .map { item ->
                                if (clickedItem == item) {
                                    item
                                } else {
                                    item
                                }
                            }
                            .toList()

                        newList
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DragNDropItemsList(
                        itemsStateFlow = itemsStateFlow,
                        onItemClicked = ::onItemClicked
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoListApp(innerPadding: PaddingValues) {


}

@Composable
fun DragNDropItemsList(
    itemsStateFlow: MutableStateFlow<List<Task>>,
    onItemClicked: (Task) -> Unit = {}
) {
    fun swapItems(from: Int, to: Int) {
        itemsStateFlow.update {
            val newList = it.toMutableList()
            val fromItem = it[from].copy()
            val toItem = it[to].copy()
            newList[from] = toItem
            newList[to] = fromItem

            println("it: $it, newList: $newList")

            newList
        }
    }

    DragDropColumn(
        items = itemsStateFlow.collectAsState().value,
        onSwap = ::swapItems
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

