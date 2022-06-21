package com.example.projettdm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*


class ParkingListFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var parkingViewModel: ParkingViewModel
   lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_parking_list, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)

        recyclerView=  view.findViewById<RecyclerView>(R.id.recyclerView)
       recyclerView.layoutManager  = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        if(parkingViewModel.data.size <= 0)
        {
            getParkings()
        }
        else
        {
            recyclerView.adapter = MyAdapter(requireActivity(), parkingViewModel.data)
        }




    }



    fun getParkings(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getParkings()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null)  {

                    parkingViewModel.data = response.body()!!.toMutableList()
                  recyclerView.adapter = MyAdapter(requireActivity(), parkingViewModel.data)

                } else
                {



                }
            }
        }
    }
    }


