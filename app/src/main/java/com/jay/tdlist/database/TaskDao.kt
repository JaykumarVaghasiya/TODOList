package com.jay.tdlist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM  tasks")
    fun getAllTask(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)
    @Transaction
    @Query("SELECT * FROM tasks WHERE taskId=:taskId" )
    fun getTaskWithToDo(taskId:Int):Flow<List<TaskToToDo>>

}
