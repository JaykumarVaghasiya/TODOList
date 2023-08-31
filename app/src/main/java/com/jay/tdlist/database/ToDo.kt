package com.jay.tdlist.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(
    entity = Task::class,
    parentColumns = arrayOf("taskId"),
    childColumns =arrayOf("fkTaskId"),
    onDelete = ForeignKey.CASCADE
)]
)

data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val fkTaskId:Int,
    val toDoName:String,
    val subtitle:String,
    val priority:Int,
   )
