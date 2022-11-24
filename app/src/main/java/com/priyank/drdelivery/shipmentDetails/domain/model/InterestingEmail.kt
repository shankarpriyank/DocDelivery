package com.priyank.drdelivery.shipmentDetails.domain.model

data class InterestingEmail(
    val trackingLink: String,
    val sentFrom: String,
    val dateOfReceivingEmail: String,
    val expectedDateOfDelivery: String?
) {
    override fun toString(): String {
        return "Email was sent from $sentFrom on $dateOfReceivingEmail with tracking link $trackingLink"
    }
}
