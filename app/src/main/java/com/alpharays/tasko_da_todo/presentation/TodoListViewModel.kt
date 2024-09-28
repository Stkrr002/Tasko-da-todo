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
        // Initialize with dummy data for testing
        initializeDummyData()

        // Uncomment this block when you want to fetch from the database
        /*
        viewModelScope.launch {
            getTasksUseCase().collect { taskList ->
                _tasks.value = taskList
            }
        }
        */
    }

    private fun initializeDummyData() {
        _tasks.value = listOf(
            Task(id = 1, title = "Task 1", description = "Description 1", position = 0),
            Task(id = 2, title = "Task 2", description = "Description 2", position = 1),
            Task(id = 3, title = "Task 3", description = "Description 3", position = 2),
            Task(id = 4, title = "Task 4", description = "Description 4", position = 3),
            Task(id = 5, title = "Task 5", description = "Description 5", position = 4)
        )
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

    fun reorderTasks(fromIndex: Int, toIndex: Int) {
        val updatedTasks = _tasks.value.toMutableList()
        val movedTask = updatedTasks.removeAt(fromIndex)
        updatedTasks.add(toIndex, movedTask)

        // Update the positions in the database
        viewModelScope.launch {
            updatedTasks.forEachIndexed { index, task ->
                reorderTasksUseCase(task.id, index)
            }
        }

        _tasks.value = updatedTasks
    }
}