package com.priyank.drdelivery.shipmentDetails.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.priyank.drdelivery.R
import com.priyank.drdelivery.shipmentDetails.TrackShipmentViewModel
import com.priyank.drdelivery.shipmentDetails.presentation.composables.ShipmentItem
import com.priyank.drdelivery.ui.theme.Lato
import com.priyank.drdelivery.ui.theme.LatoLightItalic

@Composable
fun TrackingDetailScreen(viewModel: TrackShipmentViewModel = hiltViewModel()) {

    var isloaded = viewModel.areEmailsLoaded.collectAsState(initial = false).value

    LaunchedEffect(key1 = true) {
        viewModel.fetchEmails()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(modifier = Modifier) {
                Text(
                    text = "Hi, Friend", color = Color.LightGray, fontFamily = LatoLightItalic,
                    modifier = Modifier.padding(start = 25.dp, top = 30.dp),
                    fontSize = 28.sp,
                )

                Box(modifier = Modifier) {
                    Text(
                        text = viewModel.userName, color = Color.Black, fontFamily = Lato,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(start = 30.dp, bottom = 20.dp),
                        fontSize = 26.sp
                    )

                    Button(
                        onClick = { viewModel.fetchEmails() },
                        modifier = Modifier
                            .padding(start = 300.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            disabledBackgroundColor = Color.Transparent,

                        ),
                        elevation = null
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "refresh",
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }
                }

                Divider(
                    color = Color.LightGray,
                    thickness = 2.dp,
                    startIndent = 25.dp,
                    modifier = Modifier.padding(end = 25.dp, bottom = 30.dp)
                )
                Text(
                    text = "Shipments",
                    modifier = Modifier.padding(start = 124.dp),
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                if (isloaded) {

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        for (i in 0 until viewModel.linksFromEmails.size) {

                            Log.e("RENDER NO $i", "Total ${viewModel.linksFromEmails.size}")
                            ShipmentItem(
                                providerName = viewModel.linksFromEmails[i].sentFrom,
                                trackingLink = viewModel.linksFromEmails[i].trackingLink,
                                estimatedDateOfDelivery = null ?: "N/A"
                            )
                        }
                    }
                } else {
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec
                            .RawRes(R.raw.loading)
                    )
                    val progress by animateLottieCompositionAsState(
                        composition,

                        iterations = LottieConstants.IterateForever,
                        isPlaying = true,
                        speed = .5f,
                        ignoreSystemAnimatorScale = true

                    )
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
