package com.example.ProjetParking.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ProjetParking.Models.ReservationModel

class ReservationViewModel : ViewModel(){
    var data = mutableListOf<ReservationModel>()
}