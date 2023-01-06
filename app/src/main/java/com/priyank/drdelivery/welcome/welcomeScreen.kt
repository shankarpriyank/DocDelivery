package com.priyank.drdelivery.welcome

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.offlineShipmentDetails.presentaion.SMSPermissionScreen
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel
import com.priyank.drdelivery.ui.theme.buttonBlue

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: TrackShipmentViewModel = hiltViewModel()
) {
    var isDialogShow by remember { mutableStateOf(false) }
    fun onDismiss() {
        isDialogShow = false
    }

    fun onClick() {
        isDialogShow = true
    }

    val activity = LocalContext.current as Activity
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "  Welcome to our app!",
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 25.dp, top = 30.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "  Choose what you want to track",
            color = Color.LightGray,
            modifier = Modifier.padding(start = 30.dp, bottom = 20.dp),
            fontSize = 22.sp
        )
        Divider(
            color = Color.LightGray,
            thickness = 2.dp,
            startIndent = 25.dp,
            modifier = Modifier.padding(end = 25.dp, bottom = 30.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    if (viewModel.onlineMode) {
                        navHostController.navigate(Screen.Detail.route)
                    } else {
                        onClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonBlue,
                    disabledBackgroundColor = buttonBlue
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Sync Shipments",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = if (!viewModel.onlineMode) {
                            "Auto sync tracking from your SMS by one click"
                        } else {
                            "Auto sync tracking from your emails by one click"
                        },
                        color = Color.LightGray,
                        fontSize = 15.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonBlue,
                    disabledBackgroundColor = buttonBlue
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "Track your couriers",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Track your courier shipments using tracking ID",
                        color = Color.LightGray,
                        fontSize = 15.sp
                    )
                }
            }
        }
        if (!viewModel.onlineMode) {
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(modifier = Modifier.width(60.dp), thickness = 1.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("OR", fontSize = 18.sp, color = Color.LightGray)
                    Spacer(modifier = Modifier.width(5.dp))
                    Divider(modifier = Modifier.width(60.dp), thickness = 1.dp, color = Color.LightGray)
                }
                TextButton(
                    {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Authentication.route)
                    },
                ) {
                    Text("Signin", fontSize = 20.sp, color = Color.Black)
                }
            }
        }
    }
    if (isDialogShow)
        SMSPermissionScreen({ onDismiss() }, navHostController, activity)
}
