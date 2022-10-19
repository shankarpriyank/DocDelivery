package com.priyank.drdelivery.authentication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priyank.drdelivery.authentication.model.Info
import com.priyank.drdelivery.ui.theme.Lato
@Composable
fun Slider(info: Info) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = info.image), contentDescription = null,
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(.8f)
        )
        Text(
            text = info.title,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
            fontFamily = Lato,
            fontSize = 20.sp, textAlign = TextAlign.Center
        )
    }
}
