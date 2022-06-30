package com.example.projettdm

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginuidesign.AppDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import java.nio.channels.Channel


class ReservationFragment : Fragment() {

    lateinit var navController: NavController


    var parkings = mutableListOf<ParkingModel>()
    var reservations = mutableListOf<ReservationModel>()
    var places = mutableListOf<PlaceModel>()
    var users = mutableListOf<UserModel>()
    lateinit var reservationListViewModel: ReservationListViewModel


    lateinit var recyclerView: RecyclerView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_reservation, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        reservationListViewModel =
            ViewModelProvider(requireActivity()).get(ReservationListViewModel::class.java)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewreservation)
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        val app = AppDatabase.buildDatabase(requireContext())


            getReservations()
            getParkings()
            getPlaces()
            getUsers()

        GlobalScope.launch {
            suspend@ for (res in parkings) {

                app?.getParkingDo()?.addParkings(res)

            }

            suspend@ for (res in users) {

                app?.getUserDo()?.addUsers(res)

            }

            suspend@ for (res in places) {

                app?.getPlacegDo()?.addPlaces(res)

            }

            suspend@ for (res in reservations) {

                app?.getReservationDo()?.addReservation(res)

            }

        }




        if (reservationListViewModel.data.size <= 0) {


            reservationListViewModel.data = reservations


        } else {
            //recyclerView.adapter = ReservationAdapter(requireActivity(), reservationListViewModel.data )
        }


    }


    fun getReservations() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur 1 s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getReservations()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null) {


                    //for (reservation in response.body()!!.toMutableList()){
                    //app?.getReservationDo()?.addReservation(reservation)
                    //}
                    //app?.getReservationDo()?.addReservation(reservation2)

                    reservations = response.body()!!.toMutableList()
                    recyclerView.adapter = ReservationAdapter(requireActivity(), reservationListViewModel.data)



                } else {


                }


            }
        }
    }

    fun getParkings() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur2 s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getParkings()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null) {


                    parkings = response.body()!!.toMutableList()

                } else {


                }


            }
        }
    }


    fun getPlaces() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur 3 s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getPlaces()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null) {


                    //for (reservation in response.body()!!.toMutableList()){
                    //app?.getReservationDo()?.addReservation(reservation)
                    //}
                    //app?.getReservationDo()?.addReservation(reservation2)
                    places = response.body()!!.toMutableList()

                } else {


                }


            }
        }


    }

    fun getUsers(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur 4 s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getUsers()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null)  {


                    //for (reservation in response.body()!!.toMutableList()){
                    //app?.getReservationDo()?.addReservation(reservation)
                    //}
                    //app?.getReservationDo()?.addReservation(reservation2)
                    users = response.body()!!.toMutableList()

                } else
                {


                }


            }
        }


    }

}