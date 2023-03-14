package com.priyank.drdelivery.authentication.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
import com.priyank.drdelivery.offlineShipmentDetails.presentaion.OfflineButton
import com.priyank.drdelivery.offlineShipmentDetails.presentaion.SMSPermissionScreen
import com.priyank.drdelivery.offlineShipmentDetails.presentaion.SkipButton
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

    val context = LocalContext.current
    val activity = context as Activity
    val signInRequestCode = 1
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
                        navHostController = navHostController,
                        screen = viewModel.isOfflineChosen.value
                    )
                    if (!viewModel.isOfflineChosen.value && !viewModel.isSignInChosen.value) {
                        viewModel.onSignInChosen()
                    }
                } else {
                    Log.e("Login Failed", "Error")
                }
            } catch (e: ApiException) {
                Log.e("Error in AuthScreen%s", e.toString())
            }
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
        if (!viewModel.isOfflineChosen.value && !viewModel.isSignInChosen.value) {
            SignInButton(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = false,
                icon = painterResource(id = R.drawable.ic_google_login),
                onClick = {
                    authResultLauncher.launch(signInRequestCode)
                },
            )
        }
        if (!viewModel.isOfflineChosen.value && !viewModel.isSignInChosen.value) {
            OfflineButton {
                viewModel.onClick()
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_SMS
                    ) -> {
                        viewModel.onOfflineChosen()
                    }
                    else -> {}
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
        if (viewModel.isDialogShow.value) {
            SMSPermissionScreen(
                { viewModel.onDismiss() }, navHostController, activity, viewModel.isSignInChosen.value
            )
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_SMS
                ) -> {
                    viewModel.onOfflineChosen()
                }
                else -> {}
            }
        }
        if (viewModel.isOfflineChosen.value) {
            SignInButton(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = false,
                icon = painterResource(id = R.drawable.ic_google_login),
                onClick = {
                    authResultLauncher.launch(signInRequestCode)
                    viewModel.updateUserPer(false, false, true)
                },
            )
            SkipButton(navHostController) { viewModel.updateUserPer(true, false, false) }
        }

        if (viewModel.isSignInChosen.value) {
            OfflineButton {
                viewModel.onClick()
                viewModel.updateUserPer(false, false, true)
            }
            SkipButton(
                navHostController
            ) { viewModel.updateUserPer(false, true, false) }
        }
    }
}
