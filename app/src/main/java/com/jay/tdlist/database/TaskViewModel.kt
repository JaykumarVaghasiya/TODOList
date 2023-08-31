package com.jay.tdlist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

    class TaskViewModel(application: Application):AndroidViewModel(application) {

        val allTasks: Flow<List<Task>> = TDList.repository.getAllTask()

    fun insertTask(task: Task){
        viewModelScope.launch{
            TDList.repository.insertTask(task)
        }
    }
        fun deleteTask(task: Task){
            viewModelScope.launch {
                TDList.repository.deleteTask(task)
            }
        }

}