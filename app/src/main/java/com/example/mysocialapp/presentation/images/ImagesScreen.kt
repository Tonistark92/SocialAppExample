package com.example.mysocialapp.presentation.images

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImagesScreen() {
    var auth: FirebaseAuth = Firebase.auth
    var token: String = ""
    val context = LocalContext.current
    val imgRef = Firebase.storage.reference
    val ll = imgRef.child("images/")
    val viewModle = viewModel<ImagesScreenViewmodle>()
    val state = viewModle.state
    val load = remember {
        mutableStateOf(false)
    }
    val imgUri = remember {
        mutableStateOf<Uri?>(null)
    }
    var mydownlist by remember { mutableStateOf(mutableListOf<Uri>()) }
    var done by remember { mutableStateOf(false) }
    val selectImagelauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> imgUri.value = uri })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                selectImagelauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }) {
                Text("pick image")
            }
            Button(onClick = {
                uploadImageToStorage(
                    imgUri = imgUri.value!!,
                    context = context,
                    filename = "My Image " + UUID.randomUUID()
                )
            }) {
                Text("upload image")
            }

            Button(onClick = {
                if (state.downloadedUris.value.isNotEmpty()) {
                    done = true
                }
            }) {
                Text("download images")
            }

        }
        if (state.downloadedUris.value.isNotEmpty()) {
            done = true
        }
        if (done) {
            Toast.makeText(context, "DONE!", Toast.LENGTH_LONG).show()
            Log.d("TAG", state.downloadedUris.value.toString())
            Spacer(modifier = Modifier.size(10.dp))

            Text(text = "HEEEEEEy")
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){
                items( state.downloadedUris.value.size){
                    AsyncImage(
                        model = state.downloadedUris.value[it],
                        contentDescription = "",
                        modifier = Modifier.size(400.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        ImageItem(list = state.downloadedUris.value)
    }

}

private fun uploadImageToStorage(filename: String, context: Context, imgUri: Uri) =
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val imgRef = Firebase.storage.reference
            imgUri?.let {
                imgRef.child("images/$filename").putFile(it).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, "Successfully uploaded image", Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

private fun downloadImage(imageName: String, context: Context, onDownloadComplete: (Uri?) -> Unit) =
    CoroutineScope(Dispatchers.IO).launch {
        val imgRef = Firebase.storage.reference
        val imageRef = imgRef.child("images/$imageName")// Adjust the path as needed

        // Create a local file to save the downloaded image
        val localFile = File(context.getExternalFilesDir(null), imageName)

        imageRef.getFile(localFile).addOnSuccessListener { taskSnapshot ->
            // Image download successful, and it's saved in localFile
            val uri = Uri.fromFile(localFile)

            onDownloadComplete(uri)
        }.addOnFailureListener { exception ->
            // Handle any errors here
            onDownloadComplete(null) // Return null if download fails
        }

    }

@Composable
fun ImageItem(list: MutableList<Uri>) {
    if (list.isNotEmpty()){
        Log.d("TAG", "hashdhasdhsahd")
        Text(text = "looool")
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list.size) {
                Image(
                    painter = rememberImagePainter(list[it]),
                    contentDescription = null, // Provide a description if needed
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }
 
}