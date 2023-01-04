package com.priyank.drdelivery.offlineShipmentDetails.Presentaion

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OfflineMButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(contentColor = Color(175, 202, 219))
    ) {
        Text(" Use Offline Mode ")
    }
}
