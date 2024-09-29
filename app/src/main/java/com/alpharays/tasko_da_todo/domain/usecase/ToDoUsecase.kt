package com.alpharays.tasko_da_todo.domain.usecase

import com.alpharays.tasko_da_todo.data.localdatasource.entity.Task
import com.alpharays.tasko_da_todo.data.repository.TaskRepository
import com.alpharays.tasko_da_todo.domain.mapper.toTask
import com.alpharays.tasko_da_todo.domain.mapper.toTaskData
import com.alpharays.tasko_da_todo.domain.model.TaskData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskData) = repository.insert(task.toTask())
}

class EditTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskData) = repository.update(task.toTask())
}

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskData) = repository.delete(task.toTask())
}

class GetTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskData>> = repository.allTasks.map { taskDataList ->
        taskDataList.map { task: Task ->
            task.toTaskData()

        }

    }
}

class ReorderTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int, position: Int) = repository.updatePosition(id, position)
}