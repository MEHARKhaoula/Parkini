package com.example.ProjetParking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ProjetParking.Endpoint
import com.example.ProjetParking.Models.UserModel
import com.example.ProjetParking.R
import com.example.ProjetParking.ViewModels.UserViewModel
import kotlinx.coroutines.*


class fragment_inscription : Fragment() {


    lateinit var signUp: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var telephone: EditText
    lateinit var name :EditText
    lateinit var surname :EditText
    lateinit var navController: NavController
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inscription, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




         signUp= view.findViewById(R.id.signup) as Button
         email= view.findViewById(R.id.useremail) as EditText
         password = view.findViewById(R.id.userpassword) as EditText
         telephone = view.findViewById(R.id.usertelephone) as EditText
         name = view.findViewById(R.id.username)as EditText
         surname = view.findViewById(R.id.usersurname) as EditText
         navController = Navigation.findNavController(view)
         userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)








        signUp.setOnClickListener {
            val user = UserModel(70,surname.text.toString(),name.text.toString(),telephone.text.toString(),email.text.toString(),password.text.toString())

            setUser(user)
              navController.navigate(R.id.action_fragment_inscription_to_loginFragment2)

        }


    }


    fun setUser(user: UserModel) {
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("222    "+throwable.localizedMessage)

        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().setUser(user)
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