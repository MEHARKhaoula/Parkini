package com.example.ProjetParking.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.ProjetParking.Models.PlaceModel

@Dao
interface PlaceDao {

    @Insert
    fun addPlaces(vararg place: PlaceModel)

}