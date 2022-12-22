package com.priyank.drdelivery.offlineShipmentDetails.Domain

import androidx.lifecycle.ViewModel
import com.priyank.drdelivery.offlineShipmentDetails.Domain.model.RequiredSMS

class SMSViewModel() : ViewModel() {
    var smsList = mutableListOf<RequiredSMS>()
}
