package com.example.projettdm

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName ="users" )
data class UserModel(
    @PrimaryKey()
    val iduser:Int,
    val nom:String,
    val prenom :String,
    val numero_telephone:String,
    val email:String,
    val mot_de_passe:String,



)

