package com.example.mysocialapp.presentation.images

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysocialapp.presentation.sign_in.SignInState
import com.example.mysocialapp.presentation.sign_in.utils.SignInResult
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class ImagesScreenViewmodle: ViewModel() {
    var state by mutableStateOf(ImagesScreenState())
    val imgRef = Firebase.storage.reference
    val ll = imgRef.child("images/")
    init {

   viewModelScope.launch {
       ll.listAll().addOnSuccessListener { listResult ->
           val list = mutableListOf<String>()
           for (item in listResult.items) {
               item.downloadUrl.addOnSuccessListener {
                   Log.d(
                       "TAG",
                       it.toString(),
                   )
                   state.downloadedUris.value.add(it)
               }

           }
       }.addOnFailureListener { exception ->
           // Handle any errors here
       }
   }
    }

}
