package com.example.ProjetParking.Dao

import androidx.room.*
import com.example.ProjetParking.Models.UserModel


@Dao
interface UserDao {

    @Query("select * from users where iduser=:iduser")
    fun getUsersByFirstName(iduser:Int):List<UserModel>
    @Insert
    fun addUsers(vararg users: UserModel)
    @Update
    fun updateUser(user: UserModel)
    @Delete
    fun deleteUser(user: UserModel)
}