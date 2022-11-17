package com.priyank.drdelivery.shipmentDetails.presentation.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.priyank.drdelivery.R
import com.priyank.drdelivery.ui.theme.Lato
import com.priyank.drdelivery.ui.theme.buttonBlue

@Composable
fun ShipmentItem(providerName: String, trackingLink: String, estimatedDateOfDelivery: String?) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color(245, 245, 245),
                shape = RoundedCornerShape(CornerSize(10.dp))
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Provider Name", color = Color.LightGray, fontFamily = Lato,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 18.sp
                )

                Text(
                    text = providerName, color = Color.Black, fontFamily = Lato,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 25.dp),
                    fontSize = 18.sp
                )
            }

            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Estimated Date Of Delivery",
                    color = Color.LightGray,
                    fontFamily = Lato,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 18.sp
                )

                Text(
                    text = estimatedDateOfDelivery ?: "N/A", color = Color.Black, fontFamily = Lato,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 25.dp),
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { openlink(trackingLink, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonBlue,
                    disabledBackgroundColor = buttonBlue
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = null
                )
                Text(
                    text = "Track Current Location",
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White
                )
            }
        }
    }
}

fun openlink(link: String, context: Context) {
    val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    startActivity(context, myIntent, null)
}
