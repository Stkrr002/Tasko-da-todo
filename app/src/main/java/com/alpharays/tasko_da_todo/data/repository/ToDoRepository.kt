package com.alpharays.tasko_da_todo.data.repository

import com.alpharays.tasko_da_todo.data.dao.ToDoDao
import com.alpharays.tasko_da_todo.data.entity.toDomainModel
import com.alpharays.tasko_da_todo.data.entity.toEntity
import com.alpharays.tasko_da_todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoRepository @Inject constructor(private val dao: ToDoDao) {
    fun getTasks(): Flow<List<Task>> {
        return dao.getTasks(limit = 20, offset = 0).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun addTask(task: Task) {
        dao.addTask(task.toEntity())
    }

    suspend fun editTask(task: Task) {
        dao.editTask(task.toEntity())
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }

    suspend fun reorderTasks(fromIndex: Int, toIndex: Int) {
        val tasks = getTasks().firstOrNull() ?: return
        val reorderedTasks = tasks.toMutableList().apply {
            add(toIndex, removeAt(fromIndex))
        }
        reorderedTasks.forEachIndexed { index, task ->
            dao.updateTaskPosition(task.id, index)
        }
    }
}
