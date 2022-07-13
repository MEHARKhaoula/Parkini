package com.example.ProjetParking

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
import kotlinx.coroutines.*


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





        if (reservationListViewModel.data.size <= 0) {


            getReservations()


        } else {
            //recyclerView.adapter = ReservationAdapter(requireActivity(), reservationListViewModel.data )
        }


    }


    fun getReservations() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
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
                    reservationListViewModel.data = response.body()!!.toMutableList()
                    recyclerView.adapter =
                        ReservationAdapter(requireActivity(), reservationListViewModel.data)

                } else {


                }


            }
        }
    }



}