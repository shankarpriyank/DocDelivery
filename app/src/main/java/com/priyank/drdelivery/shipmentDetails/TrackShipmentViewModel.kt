package com.priyank.drdelivery.shipmentDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.offlineShipmentDetails.data.GetSMS
import com.priyank.drdelivery.offlineShipmentDetails.data.PerDetails
import com.priyank.drdelivery.offlineShipmentDetails.domain.model.RequiredSMS
import com.priyank.drdelivery.shipmentDetails.domain.model.EmailInfoState
import com.priyank.drdelivery.shipmentDetails.domain.repository.EmailRepository
import com.priyank.drdelivery.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val userDetails: UserDetails,
    private val GetSMS: GetSMS,
    private val perDetails: PerDetails,
    private val repository: EmailRepository
) : ViewModel() {

    val userName = userDetails.getUserName()!!.substringBefore(" ")
    private val _state = mutableStateOf(EmailInfoState())
    val state: State<EmailInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var areSMSLoaded = flow<Boolean> {
        emit(false)
    }
    val onlineMode = userDetails.isLoggedIn()
    var smsList: MutableSet<RequiredSMS> = mutableSetOf()

    fun fetchSMS() {
        GlobalScope.launch {
            areSMSLoaded = flow {
                emit(false)
            }
            CoroutineScope(Dispatchers.IO).launch {
                smsList = GetSMS.getSMS()
            }
            delay(1000)
            areSMSLoaded = flow {
                emit(true)
            }
        }
    }

    suspend fun getEmails() {

        repository.getEmails().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        interestingEmail = result.data ?: emptyList(),
                        loading = false
                    )
                }

                is Resource.Loading -> {
                    if (result.data != null) {
                        if (result.data.isEmpty()) {

                            _state.value = state.value.copy(
                                interestingEmail = result.data,
                                loading = true
                            )
                        } else {

                            _state.value = state.value.copy(
                                interestingEmail = result.data,
                                loading = false
                            )
                        }
                    } else {

                        _state.value = state.value.copy(
                            interestingEmail = emptyList(),
                            loading = true
                        )
                    }
                }

                is Resource.Error -> {

                    _state.value = state.value.copy(
                        interestingEmail = result.data ?: emptyList(),
                        loading = false
                    )

                    _eventFlow.emit(UIEvent.ShowSnackbar(result.message ?: "Something Went Wrong"))
                }
            }
        }.collect {
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}
