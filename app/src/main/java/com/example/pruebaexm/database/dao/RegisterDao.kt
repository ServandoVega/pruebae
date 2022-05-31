package com.example.pruebaexm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebaexm.database.model.Register
import kotlinx.coroutines.flow.Flow

@Dao

interface RegisterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: Register)

    @Query("Select * From register")
    fun getAll(): Flow<List<Register>>
}