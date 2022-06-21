package com.example.projettdm
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface Endpoint {


    @GET("getparkings")
    suspend fun getParkings(): Response<List<ParkingModel>>


    @GET("login/{email}/{mot_de_passe}")
    suspend fun login(@Path("email") email: String, @Path("mot_de_passe") mot_de_passe: String): Response<List<UserModel>>


    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                endpoint = Retrofit.Builder().baseUrl("https://7cb4-105-235-129-171.eu.ngrok.io").addConverterFactory(
                    GsonConverterFactory.create()).build().create(
                    Endpoint::class.java)
            }
            return endpoint!!
        }

    }
}