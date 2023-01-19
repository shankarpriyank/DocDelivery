package com.priyank.drdelivery.offlineShipmentDetails.domain.model

data class RequiredSMS(
    val smsId: String,
    val smsAddress: String,
    val smsDate: String,
    val smsBody: String,
    val smsTrackLink: String
)
