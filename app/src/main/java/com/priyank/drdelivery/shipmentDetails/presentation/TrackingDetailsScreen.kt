package com.priyank.drdelivery.shipmentDetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel

@Composable
fun TrackingDetailScreen(viewModel: TrackShipmentViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = { viewModel.call() }) {
            Text(text = "Get Emails")
        }

        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Tracking Details Screen")
    }
}
