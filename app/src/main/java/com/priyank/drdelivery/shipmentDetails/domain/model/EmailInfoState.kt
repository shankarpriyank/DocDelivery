package com.priyank.drdelivery.shipmentDetails.domain.model

data class EmailInfoState(
    val interestingLink: List<InterestingLink> = emptyList(),
    val loading: Boolean = true
)
