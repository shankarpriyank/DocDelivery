package com.priyank.drdelivery.shipmentDetails.presentation

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel
import com.priyank.drdelivery.shipmentDetails.domain.BottomNavItem

@Composable
fun Greeting(
    viewModel: TrackShipmentViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    Scaffold(bottomBar = {
        BottomNavigationBar(
            items = listOf(
                BottomNavItem(
                    name = "Home",
                    route = "home",
                    icon = Icons.Outlined.Home
                ),
                BottomNavItem(
                    name = "Profile",
                    route = "profile",
                    icon = Icons.Outlined.Person
                )
            ),
            navController = navHostController,
            onItemClick = {
                // navHostController.navigate(it.route)
            }
        )
    }) {
    }

    fun signout() {
        navHostController.navigate(Screen.Authentication.route)
        Log.e("Nav", "Nav")
        viewModel.signout(navHostController)
    }
}
