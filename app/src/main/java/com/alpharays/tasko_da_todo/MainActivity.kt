package com.alpharays.tasko_da_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.alpharays.tasko_da_todo.domain.model.TaskData
import com.alpharays.tasko_da_todo.presentation.featuretodolist.TodoListScreen
import com.alpharays.tasko_da_todo.ui.theme.TaskodatodoTheme
import dagger.hilt.android.AndroidEntryPoint


data class DialogTaskData(
    val task: TaskData,
    val onSubmitClicked: (TaskData) -> Unit,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            TaskodatodoTheme {
                TodoListScreen()
            }
        }
    }
}


