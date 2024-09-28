package com.alpharays.tasko_da_todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.tasko_da_todo.data.entity.Task
import com.alpharays.tasko_da_todo.domain.AddTaskUseCase
import com.alpharays.tasko_da_todo.domain.DeleteTaskUseCase
import com.alpharays.tasko_da_todo.domain.EditTaskUseCase
import com.alpharays.tasko_da_todo.domain.GetTasksUseCase
import com.alpharays.tasko_da_todo.domain.ReorderTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val reorderTasksUseCase: ReorderTasksUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            getTasksUseCase().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            editTaskUseCase(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun reorderTasks(id: Int, position: Int) {
        viewModelScope.launch {
            reorderTasksUseCase(id, position)
        }
    }
}
