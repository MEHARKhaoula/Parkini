package com.example.projettdm

import androidx.room.*
import com.example.projettdm.UserModel

@Dao
interface ParkingDao {

    @Insert
    fun addParkings(vararg parkings: ParkingModel)

    @Query("select * from parking")
    fun getParkings():List<ParkingModel>
}