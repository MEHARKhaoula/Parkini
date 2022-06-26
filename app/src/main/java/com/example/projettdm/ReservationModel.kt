package com.example.projettdm

import java.text.SimpleDateFormat

data class ReservationModel(

    val numeroreservation:Int,
    val date: String,
    val heure_entree: String,
    val heure_sortie: String,
    val iduser:Int,
    val idplace:Int


)
