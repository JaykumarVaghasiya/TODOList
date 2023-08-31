package com.jay.tdlist.database

import android.app.Application
import androidx.room.Room

class TDList:Application() {
    companion object{
        lateinit var database: AppDatabase
            private set
        lateinit var repository: TaskRepository
        private set

        lateinit var toDoRepository: ToDoRepository
        private set
    }


    override fun onCreate() {
        super.onCreate()
        database=Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,"tdList_database"
        ).build()

        repository= TaskRepository(database.taskDao())
        toDoRepository= ToDoRepository(database.toDoDao())
    }
}
