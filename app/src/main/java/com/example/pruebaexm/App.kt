package com.example.pruebaexm

import android.app.Application
import androidx.room.Room
import com.example.pruebaexm.database.DBUser

class App: Application() {

        override fun onCreate() {
            super.onCreate()

            DBUser.db =  Room
                .databaseBuilder(this, DBUser::class.java, "user.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
