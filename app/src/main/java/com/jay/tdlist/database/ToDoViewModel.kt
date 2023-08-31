package com.jay.tdlist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application)  {

    fun getAllToDosForTask(taskId: Int): Flow<List<ToDo>> {
        return TDList.toDoRepository.getAllToDo(taskId)
    }

    fun insertToDo(toDo: ToDo,taskId:Int){
        val toDoWithTaskId=toDo.copy(fkTaskId = taskId)
        viewModelScope.launch {
            TDList.toDoRepository.insertToDo(toDoWithTaskId)
        }
    }
    fun deleteToDo(toDo: ToDo){
        viewModelScope.launch {
            TDList.toDoRepository.deleteToDo(toDo)
        }
    }

}