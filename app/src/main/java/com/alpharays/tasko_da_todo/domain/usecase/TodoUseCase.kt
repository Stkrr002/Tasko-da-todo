package com.alpharays.tasko_da_todo.domain.usecase

import com.alpharays.tasko_da_todo.data.repository.ToDoRepository
import com.alpharays.tasko_da_todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: ToDoRepository) {
    suspend operator fun invoke(task: Task) = repository.addTask(task)
}

class EditTaskUseCase @Inject constructor(private val repository: ToDoRepository) {
    suspend operator fun invoke(task: Task) = repository.editTask(task)
}

class DeleteTaskUseCase @Inject constructor(private val repository: ToDoRepository) {
    suspend operator fun invoke(task: Task) = repository.deleteTask(task)
}

class GetTasksUseCase @Inject constructor(private val repository: ToDoRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.getTasks()
}

class ReorderTaskUseCase @Inject constructor(private val repository: ToDoRepository) {
    suspend operator fun invoke(fromIndex: Int, toIndex: Int) = repository.reorderTasks(fromIndex, toIndex)
}
