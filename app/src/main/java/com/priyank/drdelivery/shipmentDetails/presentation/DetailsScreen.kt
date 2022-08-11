package com.priyank.drdelivery.shipmentDetails.presentation

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel
import com.priyank.drdelivery.shipmentDetails.domain.BottomNavItem

@Composable
fun Greeting(
    viewModel: TrackShipmentViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val navControllerForBottomNav = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(
            items = listOf(
                BottomNavItem(
                    name = "Home",
                    route = "tracking detail",
                    icon = Icons.Outlined.Home
                ),
                BottomNavItem(
                    name = "Profile",
                    route = "profile",
                    icon = Icons.Outlined.Person
                )
            ),
            navController = navControllerForBottomNav,
            onItemClick = {
                navControllerForBottomNav.navigate(it.route)
            }
        )
    }) {
        Navigation(navController = navControllerForBottomNav)
    }

    fun signout() {
        navHostController.navigate(Screen.Authentication.route)
        Log.e("Nav", "Nav")
        viewModel.signout(navHostController)
    }
}
