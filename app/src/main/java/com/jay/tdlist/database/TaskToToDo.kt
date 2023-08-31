package com.jay.tdlist.database

import androidx.room.Embedded
import androidx.room.Relation

data class TaskToToDo (
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "id"
    )
    val toDo: List<ToDo>
)