package com.priyank.drdelivery.offlineShipmentDetails.presentaion

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun OfflineButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text("Offline Mode", fontSize = 15.sp, color = Color.Black)
    }
}
