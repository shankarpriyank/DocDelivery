package com.priyank.drdelivery.shipmentDetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel

@Composable
fun TrackingDetailScreen(viewModel: TrackShipmentViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tracking Details Screen")
    }
}
