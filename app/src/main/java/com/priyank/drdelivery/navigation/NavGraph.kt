package com.priyank.drdelivery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priyank.drdelivery.authentication.presentation.LoginActivity
import com.priyank.drdelivery.feature_track_pacakges.presentation.ShipmentDetailsActivity

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Activity.Authentication.route) {
            LoginActivity()
        }
        composable(route = Activity.ShipmentDetail.route) {
            ShipmentDetailsActivity()
        }
    }
}
