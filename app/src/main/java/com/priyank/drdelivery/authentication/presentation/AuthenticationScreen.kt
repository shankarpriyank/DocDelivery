package com.priyank.drdelivery.authentication.presentation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.common.api.ApiException
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.GoogleApiContract
import com.priyank.drdelivery.authentication.LoginViewModel
import com.priyank.drdelivery.shipmentDetailsOffline.SMSPermissionScreen
import com.priyank.drdelivery.ui.theme.DarkBlue
import com.priyank.drdelivery.ui.theme.LightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthenticationScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val signInRequestCode = 1
    val showDialog = remember { mutableStateOf(false) }
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    viewModel.fetchSignInUser(
                        id = gsa.id,
                        email = gsa.email,
                        name = gsa.displayName,
                        profilePhotoUrl = gsa.photoUrl.toString(),
                        navHostController = navHostController
                    )
                } else {
                    Log.e("Login Failed", "Error")
                }
            } catch (e: ApiException) {
                Log.e("Error in AuthScreen%s", e.toString())
            }
        }
    var isDialogShown by remember { mutableStateOf(false) }
    fun onDismiss() {
        isDialogShown = false
    }

    fun onClick() {
        isDialogShown = true
    }

    val pagerState = rememberPagerState(pageCount = 3)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            Slider(info = viewModel.data()[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            indicatorShape = RectangleShape,
            activeColor = DarkBlue, inactiveColor = LightBlue
        )

        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {
                delay(3000)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }

        SignInButton(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Sign in with Google",
            loadingText = "Signing in...",
            isLoading = false,
            icon = painterResource(id = R.drawable.ic_google_login),
            onClick = { authResultLauncher.launch(signInRequestCode) },
        )
        OfflineMButton(
            modifier = Modifier,
            { onClick() }
        )
        if (isDialogShown) {
            SMSPermissionScreen({ onDismiss() })
        }
    }
}
