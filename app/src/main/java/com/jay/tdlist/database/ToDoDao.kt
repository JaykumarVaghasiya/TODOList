package com.jay.tdlist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDo WHERE fkTaskId= :taskId ORDER BY priority DESC")
    fun getAllToDos(taskId:Int): Flow<List<ToDo>>

    @Insert
    suspend fun insert(toDos: ToDo)

    @Delete
    suspend fun delete(toDos: ToDo)
}

