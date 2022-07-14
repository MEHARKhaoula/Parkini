package com.example.ProjetParking.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ProjetParking.Endpoint
import com.example.ProjetParking.Models.UserModel
import com.example.ProjetParking.R
import com.example.ProjetParking.ViewModels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class LoginFragment : Fragment() {

    lateinit var login: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var signUp: TextView
    lateinit var navController: NavController
    lateinit var userViewModel: UserViewModel

    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    lateinit var googleSignBtn : ImageView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pref = this.getActivity()?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val userConnected = pref?.getBoolean("Connected", false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)


        //sign in with google
        mAuth = FirebaseAuth.getInstance()
        googleSignBtn = view.findViewById(R.id.imageView3) as ImageView
        googleSignBtn.setOnClickListener {
            Log.d("GOOGLE_SIGN_IN_TAG","on create: begin Google S")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
            val firebaseUser = mAuth.currentUser
            if(firebaseUser != null)
            {
                pref?.edit {
                    putBoolean("Connected", true)
                }
            }

        }


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("497763760797-mo96pj3g00hf3k2fuhc18sb9pthhsggm.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)











        //sign in with email and address
        signUp= view.findViewById(R.id.TextSignUp) as TextView
        login = view.findViewById(R.id.login) as Button
        email = view.findViewById(R.id.editTextEmail) as EditText
        password = view.findViewById(R.id.editTextPassword) as EditText
        navController = Navigation.findNavController(view)






        if (userConnected == true) {
            navController.navigate(R.id.action_loginFragment_to_profileFragment)
        }


        signUp.setOnClickListener{

            navController.navigate(R.id.action_loginFragment_to_fragment_inscription)

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
                            navController.navigate(R.id.action_loginFragment_to_profileFragment)

                        } else {
                            Toast.makeText(requireActivity(), "mot de paase ou email incorrect", Toast.LENGTH_LONG).show()
                        }


                    }
                }
            }


        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100)
        {
            Log.d("GOOGLE_SIGN_IN_TAG","onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = accountTask.exception
            if(accountTask.isSuccessful)
            {
                try{
                    val account = accountTask.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                }
                catch (e: Exception)
                {
                    Log.d("GOOGLE_SIGN_IN_TAG1000", "onActivityResult: ${e.message}")
                }
            }
            else{
                Log.d("GOOGLE_SIGN_IN_TAG1000", "onActivityResult: ${exception.toString()}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        Log.d("GOOGLE_SIGN_IN_TAG","firebaseAuthWithGoogleAccount begin firebase auth with google account")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        mAuth.signInWithCredential(credential).addOnSuccessListener {
                authResult ->
            Log.d("GOOGLE_SIGN_IN_TAG","firebaseAuthGoogleAccount:Logged")
            val firebaseUser = mAuth.currentUser



           if(authResult.additionalUserInfo!!.isNewUser)
            {
                val user = UserModel(70,firebaseUser!!.displayName.toString(),firebaseUser!!.displayName.toString(),firebaseUser!!.phoneNumber.toString(),firebaseUser!!.email.toString(),"mot_de_passe")
             userViewModel.setUser(user)
             userViewModel.data.add(user)
            }

            navController.navigate(R.id.action_loginFragment_to_profileFragment)



        }
            .addOnFailureListener {
                    e->
                Log.d("GOOGLE_SIGN_IN_TAG", "firebaseAuthWithGoogleAccount: Loggin failed due to ${e.message}")
                Toast.makeText(this.requireActivity(),"Loggin failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}