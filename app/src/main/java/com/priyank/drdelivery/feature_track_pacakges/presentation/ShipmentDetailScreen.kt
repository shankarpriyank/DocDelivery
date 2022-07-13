package com.priyank.drdelivery.feature_track_pacakges.presentation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyank.drdelivery.feature_track_pacakges.TrackShipmentViewModel
import com.priyank.drdelivery.ui.theme.DrDeliveryTheme

@Composable
fun Greeting(name: String = "Hey", viewModel: TrackShipmentViewModel = hiltViewModel()) {
    Text(text = "Hello $name!")
    Button(onClick = { viewModel.signout() }) {
        Text(text = "Signout")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrDeliveryTheme {
        Greeting("Android")
    }
}
