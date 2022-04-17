package com.priyank.drdelivery.authentication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.priyank.drdelivery.authentication.model.Info

@Composable
fun Slider(info: Info) {

    Image(
        painter = painterResource(id = info.image), contentDescription = null,
        modifier = Modifier.height(800.dp).padding(top = 50.dp)
    )
}
