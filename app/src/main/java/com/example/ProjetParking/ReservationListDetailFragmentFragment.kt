package com.example.ProjetParking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class ReservationListDetailFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var parkingViewModel :ParkingViewModel
    lateinit var heuredebut :String
    lateinit var heurefin : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf:SimpleDateFormat

    var  position: Int = 0
    var placeVide = mutableListOf<PlaceModel>()
    lateinit var reservationListViewModel:ReservationListViewModel
    lateinit var placeViewModel:PlaceViewModel
    var reservationAdded = mutableListOf<ReservationModel>()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_list_detail , container, false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        //reserver = view.findViewById(R.id.payer) as Button
        navController = Navigation.findNavController(view)
        reservationListViewModel = ViewModelProvider(requireActivity()).get(ReservationListViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)


        position= arguments?.getInt("position")!!





        val reservation= position?.let { reservationListViewModel.data.get(it) }
        if (reservation != null) {

            view.findViewById<TextView>(R.id.numeroreservation).text =reservation.numeroreservation.toString()

        }




    }





}













