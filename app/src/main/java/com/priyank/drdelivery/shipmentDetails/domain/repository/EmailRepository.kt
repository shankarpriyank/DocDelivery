package com.priyank.drdelivery.shipmentDetails.domain.repository

import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import com.priyank.drdelivery.util.Resource
import kotlinx.coroutines.flow.Flow

interface EmailRepository {

    fun getEmails(): Flow<Resource<List<InterestingEmail>>>
}
