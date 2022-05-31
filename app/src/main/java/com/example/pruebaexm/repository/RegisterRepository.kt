package com.example.pruebaexm.repository

import com.example.pruebaexm.database.DBUser
import com.example.pruebaexm.database.model.Register

class RegisterRepository {
    private var dbUser: DBUser = DBUser.db
    suspend fun createRegister(register: Register){
    dbUser.registerDao().insert(register)
    }
    fun getListRegister() = dbUser.registerDao().getAll()
}