package com.example.ProjetParking



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
    val nbrplaceslibres:Int,
    val nbrplaces: Int,
    val tempsestime: Double


)