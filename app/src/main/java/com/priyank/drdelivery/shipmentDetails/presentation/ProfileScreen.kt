package com.priyank.drdelivery.shipmentDetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyank.drdelivery.ui.theme.Lato
import com.priyank.drdelivery.ui.theme.LightGrey

@Preview
@Composable
fun ProfileScreen() {

    Text(
        text = "Settings",
        color = Color.Black, fontFamily = Lato,
        modifier = Modifier.padding(start = 140.dp, top = 30.dp),
        fontSize = 28.sp,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 25.dp, end = 25.dp, bottom = 80.dp)
            .background(
                color = LightGrey,
                shape = RoundedCornerShape(
                    CornerSize(10.dp)
                )
            )
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                color = Color.Black, fontFamily = Lato,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 18.sp,
                text = "Account"
            )
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                color = Color.Black, fontFamily = Lato,
                fontSize = 18.sp,
                text = "Give feedback/Request A feature"
            )
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                color = Color.Black, fontFamily = Lato,
                fontSize = 18.sp,
                text = "Sponsor/Donate"
            )
        }
    }
}
