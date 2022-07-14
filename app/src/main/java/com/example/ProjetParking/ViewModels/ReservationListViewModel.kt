package com.example.ProjetParking.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ProjetParking.Models.ReservationModel

class ReservationListViewModel : ViewModel(){
    var data = mutableListOf<ReservationModel>()
}