package com.example.mysocialapp.presentation.sign_in

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysocialapp.components.InputField
import com.example.mysocialapp.presentation.sign_in.utils.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onNavigate: (root:String) -> Unit
) {
    val context = LocalContext.current
    val viewModel = viewModel<SignInViewModel>()
    var auth: FirebaseAuth = Firebase.auth

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )
//    LaunchedEffect(key1 = state.signInError) {
//        state.signInError?.let { error ->
//            Toast.makeText(
//                context,
//                error,
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            InputField(
                valueState = email,
                labelId = "Enter Email",
                enabled = true,
                isSingleLine = true,
                onValueChange = {})
            Spacer(modifier = Modifier.size(20.dp))
            InputField(
                valueState = password,
                labelId = "Enter password",
                enabled = true,
                isSingleLine = true,
                onValueChange = {})
            Spacer(modifier = Modifier.size(20.dp))

            Button(onClick = {
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sigh", "createUserWithEmail:success")
                            val user = auth.currentUser
//                            token = user?.getIdToken(false)?.result?.token!!
//                            Log.d(
//                                "Sigh",
//                                user?.uid.toString() + "  with token ${token}",
//                                task.exception
//                            )
                            onNavigate("Images")


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Sigh", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }) {
                Text(text = "Sign in")

            }

            Button(onClick = {
                scope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }


            ) {
                Text(text = "Sign in with Google")
            }
            Spacer(modifier = Modifier.size(20.dp))

            Button(onClick = {
                // navigate to sign Up
                onNavigate("SignUp")
            }) {
                Text(text = "Sign Up")
            }
            Button(onClick = {
                // navigate to sign Up
                onNavigate("Images")
            }) {
                Text(text = "goo Images")
            }
        }
    }


}