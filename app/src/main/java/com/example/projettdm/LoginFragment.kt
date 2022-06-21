package com.example.projettdm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    lateinit var login: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var navController: NavController
    lateinit var userViewModel: UserViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        login = view.findViewById(R.id.login) as Button
        email = view.findViewById(R.id.editTextEmail) as EditText
        password = view.findViewById(R.id.editTextPassword) as EditText
        navController = Navigation.findNavController(view)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val pref = this.getActivity()?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val userConnected = pref?.getBoolean("Connected", false)
        if (userConnected == true) {
            navController.navigate(R.id.action_loginFragment_to_reservationFragment2)
        }
        login.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val response = Endpoint.createEndpoint().login(email.text.toString(), password.text.toString())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {

                        userViewModel.data = response.body()!!.toMutableList()
                        if (userViewModel.data.size > 0) {

                            pref?.edit {
                                putBoolean("Connected", true)
                            }
                            navController.navigate(R.id.action_loginFragment_to_reservationFragment2)

                        } else {
                            Toast.makeText(requireActivity(), "mot de paase ou email incorrect", Toast.LENGTH_LONG).show()
                        }


                    }
                }
            }


        }


    }



}