package com.example.pruebaexm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebaexm.database.dao.RegisterDao
import com.example.pruebaexm.database.model.Register

@Database(
    entities = [Register::class],
    version = 1,
    exportSchema = false

)

abstract class DBUser : RoomDatabase() {

    companion object {
        lateinit var db: DBUser
    }

    abstract fun registerDao(): RegisterDao
}