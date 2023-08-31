package com.jay.tdlist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class,ToDo::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase (){
    abstract fun taskDao():TaskDao
    abstract fun toDoDao():ToDoDao

}
