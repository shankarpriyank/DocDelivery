package com.priyank.drdelivery.feature_track_pacakges.presentation

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.priyank.drdelivery.feature_track_pacakges.TrackShipmentViewModel
import com.priyank.drdelivery.navigation.Screen

@Composable
fun Greeting(
    name: String = "Hey",
    viewModel: TrackShipmentViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    Text(text = "Hello $name!")
    Button(onClick = { viewModel.signout(navHostController) }) {
        Text(text = "Signout")
    }

    fun signout() {
        navHostController.navigate(Screen.Authentication.route)
        Log.e("Nav", "Nav")
        viewModel.signout(navHostController)
    }
}
