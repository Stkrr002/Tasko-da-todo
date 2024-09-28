package com.alpharays.tasko_da_todo.domain

import com.alpharays.tasko_da_todo.data.entity.Task
import com.alpharays.tasko_da_todo.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.insert(task)
}

class EditTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.update(task)
}

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) = repository.delete(task)
}

class GetTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.allTasks
}

class ReorderTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int, position: Int) = repository.updatePosition(id, position)
}