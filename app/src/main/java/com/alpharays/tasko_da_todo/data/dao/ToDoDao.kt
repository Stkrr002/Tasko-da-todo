package com.alpharays.tasko_da_todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alpharays.tasko_da_todo.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM tasks ORDER BY position ASC LIMIT :limit OFFSET :offset")
    fun getTasks(limit: Int, offset: Int): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun editTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("UPDATE tasks SET position = :newPosition WHERE id = :taskId")
    suspend fun updateTaskPosition(taskId: Int, newPosition: Int)
}
