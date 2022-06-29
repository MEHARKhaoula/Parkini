package com.example.ProjetParking

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
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
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class ParkingDetailFragment : Fragment() {
    lateinit var reserver: Button
    lateinit var navController: NavController
    lateinit var parkingViewModel:ParkingViewModel
    lateinit var heuredebut :String
    lateinit var heurefin : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf:SimpleDateFormat
    lateinit var  myCalendar:Calendar
    lateinit var parking:ParkingModel

    var  position: Int = 0
    var search:Boolean=false
    var placeVide = mutableListOf<Int>()




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
        reserver = view.findViewById(R.id.reserver) as Button
        navController = Navigation.findNavController(view)

         position= arguments?.getInt("position")!!
        search = arguments?.getBoolean("search")!!


        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        if(search == true)

             parking= position?.let { parkingViewModel.searchData.get(it) }

        else
       parking= position?.let { parkingViewModel.data.get(it) }
        if (parking != null) {
            view.findViewById<TextView>(R.id.textViewTitre).text = parking.nom
            view.findViewById<TextView>(R.id.textViewKilom).text = parking.distance.toString()
            view.findViewById<TextView>(R.id.textViewEtat).text = parking.etat
            view.findViewById<TextView>(R.id.TextViewTaux).text = (parking.nbrplaceslibres/parking.nbrplaces).toString()
            view.findViewById<TextView>(R.id.textViewTime).text = parking.heuredebut+""+"a"+""+parking.heurefin
            view.findViewById<TextView>(R.id.textViewlocation).text = parking.commune
            view.findViewById<TextView>(R.id.TextViewJour).text =""
            view.findViewById<TextView>(R.id.TextViewHeure).text = parking.tempsestime.toString()
            view.findViewById<TextView>(R.id.TextViewPrix).text = ""
        }

        reserver.setOnClickListener{



            myCalendar = Calendar.getInstance()

            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updatelabele( myCalendar)

                openTimePicker1()

            }

            DatePickerDialog(requireContext(),
                R.style.MyDatePickerDialogTheme, datePicker ,myCalendar.get(Calendar.YEAR) , myCalendar.get(Calendar.YEAR)
                , myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        }

    }


    fun updatelabele(mycalendar:Calendar){
        val myformat ="dd-MM-YYYY"
         sdf =SimpleDateFormat(myformat,Locale.FRANCE)
        Toast.makeText(requireActivity(),"Erreur!",Toast.LENGTH_LONG).show()

        print(sdf.format(mycalendar.time))


    }

    fun openTimePicker1(){

        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat= if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker1= MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Heure Debut de réservation")
            .build()

        picker1.show(childFragmentManager, "TAG")

        picker1.addOnPositiveButtonClickListener {
            heuredebut = picker1.hour.toString()+":"+picker1.minute.toString()
            print(heuredebut)

            openTimePicker2()



        }

    }


    fun openTimePicker2(){

        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat= if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker1= MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Heure fin de réservation" )
            .build()

        picker1.show(childFragmentManager, "TAG")

        picker1.addOnPositiveButtonClickListener {
            heurefin = picker1.hour.toString()+":"+picker1.minute.toString()
            print(heurefin)


            setReservation(ReservationModel(4, sdf.format(myCalendar.time) ,heuredebut ,heurefin , userViewModel.data.get(0).iduser, 1))


        }


    }

    fun getPlaceVide(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getPlaceVide(parkingViewModel.data.get(position).idparking)
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null)  {

                    placeVide = response.body()!!.toMutableList()

                } else
                {



                }
            }
        }
    }

    fun setReservation(res: ReservationModel) {
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("222    "+throwable.localizedMessage)

        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().setReservation(res)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful )  {

                } else
                {
                    Toast.makeText(requireActivity(),"Erreur!",Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}