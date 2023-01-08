package com.priyank.drdelivery.offlineShipmentDetails.presentaion

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.priyank.drdelivery.navigation.Screen

@Composable
fun SkipButton(
    navHostController: NavHostController,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            onClick()
            navHostController.navigate(Screen.Detail.route)
        },
        colors = ButtonDefaults.textButtonColors(contentColor = Color.LightGray)
    ) {
        Text("Skip")
    }
}
