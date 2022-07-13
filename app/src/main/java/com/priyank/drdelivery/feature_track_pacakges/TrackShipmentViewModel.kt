package com.priyank.drdelivery.feature_track_pacakges

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val gsc: GoogleSignInClient
) : ViewModel() {
    fun signout() {
        gsc.signOut()
    }
}
