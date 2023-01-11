package com.priyank.drdelivery.shipmentDetails.domain.model

data class EmailInfoState(
    val interestingEmail: List<InterestingEmail> = emptyList(),
    val loading: Boolean = false
)
