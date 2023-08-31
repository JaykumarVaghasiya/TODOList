package com.jay.tdlist.database

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTask(): Flow<List<Task>> {
        return taskDao.getAllTask()
    }

    suspend fun insertTask(task: Task){
        taskDao.insert(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.delete(task)
    }
}