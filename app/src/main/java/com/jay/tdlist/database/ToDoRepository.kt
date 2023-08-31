package com.jay.tdlist.database

import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) {

    fun getAllToDo(taskId:Int): Flow<List<ToDo>> {
        return toDoDao.getAllToDos(taskId)
    }

    suspend fun insertToDo(toDo: ToDo){
        toDoDao.insert(toDo)
    }

    suspend fun deleteToDo(toDo: ToDo){
        toDoDao.delete(toDo)
    }


}