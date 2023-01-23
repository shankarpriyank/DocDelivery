package com.priyank.drdelivery.authentication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.authentication.model.Info
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.offlineShipmentDetails.data.PerDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val userDetails: UserDetails,
    private val gsc: GoogleSignInClient,
    private val userPer: PerDetails,
) : ViewModel() {
    var isSignInChosen = mutableStateOf(false)
    var isOfflineChosen = mutableStateOf(false)
    var isDialogShow = mutableStateOf(false)
    fun onSignInChosen() {
        isSignInChosen.value = true
    }

    fun onOfflineChosen() {
        isOfflineChosen.value = true
    }
    fun onDismiss() {
        isDialogShow.value = false
    }

    fun onClick() {
        isDialogShow.value = true
    }
    fun data(): List<Info> {

        return listOf(
            Info(
                "Auto-track orders with \n email sync", R.drawable.ic_autotrack
            ),
            Info(
                "Get Realtime Delivery Updates", R.drawable.ic_realtime
            ),
            Info(
                "Your Data is safe, we do everything on the device itself",
                R.drawable.ic_privacy_png
            )

        )
    }

    fun fetchSignInUser(
        id: String?,
        email: String?,
        name: String?,
        profilePhotoUrl: String? = null,
        navHostController: NavHostController,
        screen: Boolean
    ) {
        Log.i("User ", "SignedIn")
        userDetails.updateUser(
            id = id,
            name = name,
            email = email,
            profilePhotoUrl = profilePhotoUrl,
        )
        Log.i("Name", name.toString())
        Log.i("Email", email.toString())
        Log.i("Url", profilePhotoUrl.toString())
        Log.i("Id", id.toString())

        if (screen) {
            navHostController.popBackStack()
            navHostController.navigate(Screen.Detail.route)
        }
    }

    fun signOutUser() {
        Log.i("Signout", "Signout Successful")
        userDetails.signOut()
        gsc.signOut()
    }

    fun updateUserPer(
        sms: Boolean,
        signIn: Boolean,
        bothPer: Boolean
    ) {
        userPer.updatePer(onlySMS = sms, onlySignIn = signIn, bothPer = bothPer)
    }
}
