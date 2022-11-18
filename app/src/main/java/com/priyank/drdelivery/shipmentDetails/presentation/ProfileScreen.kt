package com.priyank.drdelivery.shipmentDetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.priyank.drdelivery.authentication.LoginViewModel
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.ui.theme.Lato
import com.priyank.drdelivery.ui.theme.LightGrey

@Composable

fun ProfileScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {

        Text(
            text = "Settings",
            color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = Lato,
            modifier = Modifier.padding(start = 140.dp, top = 30.dp),
            fontSize = 28.sp,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .background(
                    color = LightGrey,
                    shape = RoundedCornerShape(
                        CornerSize(10.dp)
                    )
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    color = Color.Black, fontFamily = Lato,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    fontSize = 18.sp,
                    text = "Account",
                    textAlign = TextAlign.Start
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Color.Black, fontFamily = Lato,
                    fontSize = 18.sp,
                    text = "Give feedback/Request A feature",
                    textAlign = TextAlign.Start
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Color.Black, fontFamily = Lato,
                    fontSize = 18.sp,
                    text = "Report A Bug ",
                    textAlign = TextAlign.Start
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 80.dp)
                .background(
                    color = LightGrey,
                    shape = RoundedCornerShape(
                        CornerSize(10.dp)
                    )
                )
        ) {

            Button(
                onClick = {
                    singout(
                        navHostController = navHostController,
                        viewModel = viewModel
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = Lato,
                    fontSize = 18.sp,
                    text = "Sign Out",
                    textAlign = TextAlign.Start
                )
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Color.Black, fontFamily = Lato,
                    fontSize = 18.sp,
                    text = "Sponsor/Donate", fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,

                ),
                elevation = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = Lato,
                    fontSize = 18.sp,
                    text = "Tell a Friend ❤",
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

fun singout(navHostController: NavHostController, viewModel: LoginViewModel) {
    navHostController.navigate(Screen.Authentication.route) {
        popUpTo(Screen.Detail.route) {
            inclusive = true
        }
    }
    viewModel.signOutUser()
}
