package com.example.mysocialapp.presentation.SignUp

import android.util.Log
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mysocialapp.components.InputField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onNavigate: () -> Unit
) {
    val context = LocalContext.current
    var auth: FirebaseAuth = Firebase.auth
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

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
                auth.createUserWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sigh", "createUserWithEmail:success")
                            val user = auth.currentUser
                            Log.d("Sigh", user?.uid.toString(), task.exception)
                            onNavigate()

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
                Text(text = "Sign Up")
            }
        }

    }
}