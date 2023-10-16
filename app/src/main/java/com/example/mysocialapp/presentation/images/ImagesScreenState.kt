package com.example.mysocialapp.presentation.images

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ImagesScreenState(
    val downloadedUris: MutableState<MutableList<Uri>> = mutableStateOf(mutableListOf<Uri>())

)
