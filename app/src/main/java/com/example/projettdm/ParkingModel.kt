package com.example.projettdm



data class ParkingModel(
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
    val nbrplaceslibre:Int,
    val nbrplaces: Int,
    val tempsestime: Double


)