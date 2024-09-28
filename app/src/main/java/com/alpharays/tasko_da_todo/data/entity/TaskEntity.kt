package com.alpharays.tasko_da_todo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alpharays.tasko_da_todo.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val position: Int
)

fun TaskEntity.toDomainModel() = Task(id, name, position)

fun Task.toEntity() = TaskEntity(id, name, position)
