package com.priyank.drdelivery.shipmentDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.shipmentDetails.domain.model.EmailInfoState
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import com.priyank.drdelivery.shipmentDetails.domain.repository.EmailRepository
import com.priyank.drdelivery.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val gsaa: GoogleSignInAccount?,
    private val gsc: GoogleSignInClient,
    private val userDetails: UserDetails,
    private val repository: EmailRepository
) : ViewModel() {

    val userName = userDetails.getUserName()!!.substringBefore(" ")
    var linksFromEmails: MutableList<InterestingEmail> = mutableListOf()

    var areEmailsLoaded = flow<Boolean> {
        emit(false)
    }

    private val _state = mutableStateOf(EmailInfoState())
    val state: State<EmailInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun getEmails() {
        Log.e("RED", "Was")

        repository.getEmails().onEach { result ->

            Log.e("Outer", "Reach")
            when (result) {
                is Resource.Success -> {
                    Log.e("Success", "C")
                    _state.value = state.value.copy(
                        interestingEmail = result.data ?: emptyList(),
                        loading = false
                    )
                }

                is Resource.Loading -> {
                    Log.e("Loading", "C")

                    _state.value = state.value.copy(
                        interestingEmail = result.data ?: emptyList(),
                        loading = true
                    )
                }

                is Resource.Error -> {
                    Log.e("failing", "C")

                    _state.value = state.value.copy(
                        interestingEmail = result.data ?: emptyList(),
                        loading = false
                    )

                    _eventFlow.emit(UIEvent.ShowSnackbar(result.message ?: "Something Went Wrong"))
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}
