package com.priyank.drdelivery.authentication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.priyank.drdelivery.authentication.model.LoginViewModel
import com.priyank.drdelivery.ui.theme.DrDeliveryTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrDeliveryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginUi()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun LoginUi(viewModel: LoginViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState(pageCount = 3)
    Column(modifier = Modifier.fillMaxSize().padding()) {

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->

            Slider(info = viewModel.data()[page])

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 400.dp),
                indicatorShape = RectangleShape
            )
        }

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//    DrDeliveryTheme {
//        Greeting("Android")
//    }
    }
}
