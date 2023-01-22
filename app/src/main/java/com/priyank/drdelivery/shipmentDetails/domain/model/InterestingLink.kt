package com.priyank.drdelivery.shipmentDetails.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InterestingLink(
    @PrimaryKey
    val id: String,
    val trackingLink: String,
    val sentFrom: String,
    val dateOfReceivingEmail: String,
    val expectedDateOfDelivery: String?
) {
    override fun toString(): String {
        return "Email was sent from $sentFrom on $dateOfReceivingEmail with tracking link $trackingLink"
    }
}
