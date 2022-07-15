package com.example.ProjetParking.Fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ProjetParking.Endpoint
import com.example.ProjetParking.MyAdapter
import com.example.ProjetParking.ParkingViewModel
import com.example.ProjetParking.R
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*


class ParkingListFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var parkingViewModel: ParkingViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView



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
        searchView = view.findViewById(R.id.searchView)


        //resizing the searchbar
        val displayMetrics = DisplayMetrics()
        this.activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        searchView.getLayoutParams().width = (0.8*width).toInt()
       recyclerView.layoutManager  = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        if(parkingViewModel.data.size <= 0)
        {
            getParkings()
        }
        else
        {
            recyclerView.adapter = MyAdapter(requireActivity(), parkingViewModel.data,false)
        }





        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
             /*   if(query != null){
                  val p =   getLocationFromAddress(query)
                    if (p != null) {
                        search(p)
                    }

                }*/

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }


        })





    }



    fun getParkings(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {


            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getParkings()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null)  {

                    parkingViewModel.data = response.body()!!.toMutableList()
                  recyclerView.adapter = MyAdapter(requireActivity(), parkingViewModel.data,false)

                } else
                {



                }
            }
        }
    }







   /* fun getLocationFromAddress(strAddress: String?): LatLng? {
        val coder = Geocoder(this.activity)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
           address = coder.getFromLocationName(strAddress, 5)
           if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.getLatitude()
            location.getLongitude()

            p1 = LatLng(location.getLatitude(), location.getLongitude())



            return p1
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }  */


    fun search(p:LatLng)
    {
      parkingViewModel.searchData =  parkingViewModel.getSearchParking(p.latitude,p.longitude)

                recyclerView.adapter = MyAdapter(requireActivity(), parkingViewModel.searchData,true)


    }



    }


