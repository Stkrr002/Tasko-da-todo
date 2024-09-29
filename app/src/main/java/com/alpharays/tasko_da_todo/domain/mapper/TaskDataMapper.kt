package com.alpharays.tasko_da_todo.domain.mapper

import com.alpharays.tasko_da_todo.data.entity.Task
import com.alpharays.tasko_da_todo.domain.model.TaskData

fun Task.toTaskData(): TaskData {
    return TaskData(
        id = this.id,
        title = this.title,
        description = this.description,
        position = this.position
    )
}

fun TaskData.toTask(id: Int = 0): Task {
    return Task(
        id = id,
        title = this.title,
        description = this.description,
        position = this.position
    )
}