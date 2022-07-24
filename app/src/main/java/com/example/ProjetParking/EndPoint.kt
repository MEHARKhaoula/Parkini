package com.example.ProjetParking
import com.example.ProjetParking.Models.ParkingModel
import com.example.ProjetParking.Models.PlaceModel
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.Models.UserModel
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
    suspend fun getPlaceVide(@Path("idparking") idparking:Int): Response<List<PlaceModel>>


    @GET("parkingplace/{idplace}")
    suspend fun getPlaceById(@Path("idplace") idplace:Int): Response<List<PlaceModel>>

    @POST("setusers")
    suspend fun setUser(@Body user: UserModel): Response<UserModel>

    @POST("setreservation")
    suspend fun setReservation(@Body reservation: ReservationModel): Response<List<ReservationModel>>

    @GET("getreservations")
    suspend fun getReservations(): Response<List<ReservationModel>>


    @GET("getparkings")
    suspend fun getParkings(): Response<List<ParkingModel>>

    @GET("getaddesreservation")
    suspend fun getAddedReservation(): Response<List<ReservationModel>>


    @GET("getplaces")
    suspend fun getPlaces(): Response<List<PlaceModel>>

    @GET("users")
    suspend fun getUsers(): Response<List<UserModel>>


    @GET("login/{email}/{mot_de_passe}")
    suspend fun login(@Path("email") email: String, @Path("mot_de_passe") mot_de_passe: String): Response<List<UserModel>>


    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                val gson =  GsonBuilder()
                    .setDateFormat("dd-MM-YYYY")
                    .setLenient()
                    .create()
                endpoint = Retrofit.Builder().baseUrl("https://4d01-129-45-67-249.eu.ngrok.io").addConverterFactory(
                    GsonConverterFactory.create(gson)).build().create(
                    Endpoint::class.java)
            }
            return endpoint!!
        }

    }
}