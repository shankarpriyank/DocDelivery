package com.priyank.drdelivery.offlineShipmentDetails.presentaion

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.priyank.drdelivery.ui.theme.DarkBlue

@Composable
fun OfflineButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text("Offline Mode", fontSize = 15.sp, color = DarkBlue)
    }
}
