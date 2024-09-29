package com.alpharays.tasko_da_todo.domain.model


data class TaskData(
    val id: Int = 0,
    val title: String,
    val description: String,
    val position: Int
)