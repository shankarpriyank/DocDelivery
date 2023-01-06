package com.priyank.drdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priyank.drdelivery.authentication.presentation.AuthenticationScreen
import com.priyank.drdelivery.shipmentDetails.presentation.Greeting
import com.priyank.drdelivery.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Authentication.route) {
            AuthenticationScreen(navHostController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navController)
        }
        composable(route = Screen.Detail.route) {
            Greeting(navHostController = navController)
        }
    }
}
