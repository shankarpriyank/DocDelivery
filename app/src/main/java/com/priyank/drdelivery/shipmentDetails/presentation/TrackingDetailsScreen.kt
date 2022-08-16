package com.priyank.drdelivery.shipmentDetails.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun TrackingDetailScreen(viewModel: TrackShipmentViewModel = hiltViewModel()) {

    var Email: List<Message> = listOf()

    GlobalScope.launch {
        Email = viewModel.getEmails()
    }

    Log.e("Size", Email.size.toString())

    for (i in 0 until Email.size) {
        Log.e("Email no $i", Email.get(i).snippet)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tracking Details Screen")
    }
}
