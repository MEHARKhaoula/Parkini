package com.example.ProjetParking

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PlaceDao {

    @Insert
    fun addPlaces(vararg place: PlaceModel)

}