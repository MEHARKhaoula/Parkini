package com.example.ProjetParking.Dao

import androidx.room.*
import com.example.ProjetParking.Models.ParkingModel


@Dao
interface ParkingDao {

    @Insert
    fun addParkings(vararg parkings: ParkingModel)

    @Query("select * from parking")
    fun getParkings():List<ParkingModel>
}