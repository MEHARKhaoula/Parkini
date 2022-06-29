package com.example.ProjetParking
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Endpoint {


    @GET("reservationgetplace/{idparking}")
    suspend fun getPlaceVide(@Path("idparking") idparking: Int): Response<List<Int>>

    @POST("setusers")
    suspend fun setUser(@Body user:UserModel): Response<UserModel>

    @POST("setreservation")
    suspend fun setReservation(@Body reservation:ReservationModel): Response<UserModel>


    @GET("getparkings")
    suspend fun getParkings(): Response<List<ParkingModel>>


    @GET("login/{email}/{mot_de_passe}")
    suspend fun login(@Path("email") email: String, @Path("mot_de_passe") mot_de_passe: String): Response<List<UserModel>>


    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                val gson =  GsonBuilder()
                    .setDateFormat("dd-MM-YYYY")
                    .create()
                endpoint = Retrofit.Builder().baseUrl("https://94b0-129-45-25-201.eu.ngrok.io").addConverterFactory(
                    GsonConverterFactory.create(gson)).build().create(
                    Endpoint::class.java)
            }
            return endpoint!!
        }

    }
}