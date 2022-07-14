package com.example.ProjetParking.Models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName ="place", foreignKeys = [ForeignKey(entity= ParkingModel::class,
    parentColumns=["idparking"] ,childColumns = ["idparking"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE ), ])
data class PlaceModel(
    @PrimaryKey()
    val idplace:Int ,
    val occupe: String,
    val numero_place:Int,
    val idparking:Int


)
