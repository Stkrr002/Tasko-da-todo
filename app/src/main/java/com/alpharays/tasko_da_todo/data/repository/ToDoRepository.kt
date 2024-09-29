package com.alpharays.tasko_da_todo.data.repository

import com.alpharays.tasko_da_todo.data.dao.TaskDao
import com.alpharays.tasko_da_todo.data.entity.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()



    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun updatePosition(id: Int, position: Int) {
        taskDao.updateTaskPosition(id, position)
    }
}
