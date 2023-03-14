package com.priyank.drdelivery

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.offlineShipmentDetails.data.PerDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject
constructor(
    private val userDetails: UserDetails,
    private val perDetails: PerDetails
) : ViewModel() {

    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()
    private val _startDestination: MutableState<String> =
        mutableStateOf(Screen.Authentication.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            val isUserLoggedIn = userDetails.isLoggedIn()
            if (perDetails.bothPerGranted()) {
                Log.e("Is user signed in", isUserLoggedIn.toString())
                _startDestination.value = Screen.Detail.route
            } else {
                Log.i("Is user signed in", isUserLoggedIn.toString())
                _startDestination.value = Screen.Authentication.route
            }
            _showSplashScreen.emit(false)
        }
    }
}
