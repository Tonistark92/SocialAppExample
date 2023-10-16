package com.example.mysocialapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysocialapp.presentation.SignUp.SignUpScreen
import com.example.mysocialapp.presentation.images.ImagesScreen
import com.example.mysocialapp.presentation.sign_in.SignInScreen

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Screens.SignInScreen.name
    ) {

        composable(route = Screens.ImagesScreen.name) {
            ImagesScreen()
        }
        composable(route = Screens.SignInScreen.name) {
            SignInScreen {
                when (it) {
                    "Images" -> {
                        navController.navigate(Screens.ImagesScreen.name)
                    }

                    "SignUp" -> {
                        navController.navigate(Screens.SignUpScreen.name)
                    }
                }
            }
        }
        composable(route = Screens.SignUpScreen.name) {
            SignUpScreen {
                navController.navigate(Screens.SignInScreen.name)
            }
        }
    }

}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val ONBOARDING = "onboarding_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
    const val REQUESTS = "requests_graph"
}