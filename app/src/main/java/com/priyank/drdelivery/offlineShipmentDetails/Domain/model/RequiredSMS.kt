package com.priyank.drdelivery.offlineShipmentDetails.Domain.model

data class RequiredSMS(
    val id: Int,
    val address: String,
    val date: String,
    val body: String,
    val link: String
)
