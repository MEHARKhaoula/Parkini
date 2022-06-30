package com.example.projettdm

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


class ParkingDetailFragment : Fragment() {
    lateinit var reserver: Button
    lateinit var navController: NavController
    lateinit var parkingViewModel :ParkingViewModel
    lateinit var heuredebut :String
    lateinit var heurefin : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf:SimpleDateFormat
    lateinit var  myCalendar:Calendar
    var  position: Int = 0
    var placeVide = mutableListOf<PlaceModel>()
    lateinit var reservationViewModel:ReservationViewModel
    lateinit var placeViewModel:PlaceViewModel
    var reservationAdded = mutableListOf<ReservationModel>()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parking_detail, container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        reserver = view.findViewById(R.id.payer) as Button
        navController = Navigation.findNavController(view)
        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)




        position= arguments?.getInt("position")!!




        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        val parking= position?.let { parkingViewModel.data.get(it) }
        if (parking != null) {
            view.findViewById<TextView>(R.id.textViewTitre).text = parking.nom
            view.findViewById<TextView>(R.id.textViewKilom).text = parking.distance.toString()
            view.findViewById<TextView>(R.id.textViewEtat).text = parking.etat
            view.findViewById<TextView>(R.id.TextViewTaux).text = (parking.nbrplaceslibre/parking.nbrplaces).toString()
            view.findViewById<TextView>(R.id.textViewTime).text = parking.heuredebut+""+"a"+""+parking.heurefin
            view.findViewById<TextView>(R.id.textViewdate).text = parking.commune
            view.findViewById<TextView>(R.id.numeroreservation).text =""
            view.findViewById<TextView>(R.id.TextViewHeure).text = parking.tempsestime.toString()
            view.findViewById<TextView>(R.id.TextViewPrix).text = ""






            System.out.print(placeVide.toString())
            //Toast.makeText(requireActivity(),placeVide.toString(),Toast.LENGTH_LONG).show()


        }



        reserver.setOnClickListener{


            val pref = this.getActivity()?.getSharedPreferences("data", Context.MODE_PRIVATE)
            val userConnected = pref?.getBoolean("Connected", false)
            if (userConnected == true) {

                navController.navigate(R.id.action_parkingDetailFragment_to_paymentFragment2)


            }else{

                Toast.makeText(requireActivity(),"You should login to accomplish this action",Toast.LENGTH_LONG).show()


            }



        }

    }





    }













