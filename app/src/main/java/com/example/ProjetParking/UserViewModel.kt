package com.example.ProjetParking

import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class UserViewModel : ViewModel(){
    var data = mutableListOf<UserModel>()
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
                    // Toast.makeText(requireActivity(),"Erreur!", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}


