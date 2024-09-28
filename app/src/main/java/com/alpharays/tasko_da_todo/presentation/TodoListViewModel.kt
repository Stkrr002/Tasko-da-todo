package com.alpharays.tasko_da_todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.tasko_da_todo.domain.model.Task
import com.alpharays.tasko_da_todo.domain.usecase.AddTaskUseCase
import com.alpharays.tasko_da_todo.domain.usecase.DeleteTaskUseCase
import com.alpharays.tasko_da_todo.domain.usecase.EditTaskUseCase
import com.alpharays.tasko_da_todo.domain.usecase.GetTasksUseCase
import com.alpharays.tasko_da_todo.domain.usecase.ReorderTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val reorderTaskUseCase: ReorderTaskUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks->
                _tasks.value = tasks
            }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch {
        addTaskUseCase(task)
    }

    fun editTask(task: Task) = viewModelScope.launch {
        editTaskUseCase(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        deleteTaskUseCase(task)
    }

    fun reorderTask(fromIndex: Int, toIndex: Int) = viewModelScope.launch {
        reorderTaskUseCase(fromIndex, toIndex)
    }
}
