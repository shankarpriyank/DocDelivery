package com.priyank.drdelivery.shipmentDetails.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TrackingDetailScreen(
    viewModel: TrackShipmentViewModel = hiltViewModel(),

) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.getEmails()
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TrackShipmentViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
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
                        text = if (viewModel.onlineMode) viewModel.userName
                        else "Offline User",
                        color = Color.Black, fontFamily = Lato,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(start = 30.dp, bottom = 20.dp),
                        fontSize = 26.sp
                    )

                    Button(
                        onClick = { GlobalScope.launch(Dispatchers.IO) { viewModel.getEmails() } },
                        modifier = Modifier
                            .padding(start = 300.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            disabledBackgroundColor = Color.Transparent,

                        ),
                        elevation = null
                    ) {
                        Image(
                            painter = painterResource(
                                if (viewModel.onlineMode) R.drawable.ic_refresh
                                else R.drawable.offbutt_removebg_preview
                            ),
                            contentDescription = "refresh",
                            modifier = if (viewModel.onlineMode) Modifier.background(Color.Transparent)
                            else Modifier
                                .background(Color.Transparent)
                                .size(50.dp)
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

                Scaffold(scaffoldState = scaffoldState) {
                    if (!state.loading) {
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            for (i in 0 until state.interestingLink.size) {
                                Log.i(
                                    "RENDER NO $i",
                                    "Total ${state.interestingLink.size}"
                                )
                                ShipmentItem(
                                    providerName = state.interestingLink[i].sentFrom,
                                    trackingLink = state.interestingLink[i].trackingLink,
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
}
