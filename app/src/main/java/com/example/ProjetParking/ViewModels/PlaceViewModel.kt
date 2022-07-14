package com.example.ProjetParking.ViewModels

import androidx.lifecycle.ViewModel
import com.example.ProjetParking.Models.PlaceModel

class PlaceViewModel : ViewModel(){
    var data = mutableListOf<PlaceModel>()
}