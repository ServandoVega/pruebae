package com.example.pruebaexm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "register")
data class Register(@PrimaryKey(autoGenerate = true) var id: Long,
                    var name: String,
                    var surnames: String,
                    var phone: String,
                    var email: String,
                    var image: ByteArray,
                    var latitude: Double,
                    var longitude: Double)

