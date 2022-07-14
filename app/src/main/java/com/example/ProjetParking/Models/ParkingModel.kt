package com.example.ProjetParking.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="parking" )
data class ParkingModel(
    @PrimaryKey()
    val idparking:Int,
    val commune:String,
    val nom :String,
    val etat:String,
    val photo:String,
    val heuredebut:String,
    val distance: Double ,
    val heurefin:String,
    val longitude: Double,
    val latitude: Double,
    val nbrplaceslibres:Int,
    val nbrplaces: Int,
    val tempsestime: Double,
    val tarif:Double



)