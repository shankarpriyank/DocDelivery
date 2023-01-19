package com.priyank.drdelivery.shipmentDetails.data.repository

import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.offlineShipmentDetails.data.GetSMS
import com.priyank.drdelivery.shipmentDetails.data.local.InteresingEmailDao
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import com.priyank.drdelivery.shipmentDetails.domain.repository.EmailRepository
import com.priyank.drdelivery.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmailRepositoryImpl @Inject
constructor(
    private val emailDao: InteresingEmailDao,
    private val getEmails: GetEmails,
    private val userDetails: UserDetails,
    private val GetSMS: GetSMS,
) : EmailRepository {
    val onlineMode = userDetails.isLoggedIn()
    override fun getEmails(): Flow<Resource<List<InterestingEmail>>> {
        return flow {
            emit(Resource.Loading())
            val interestingEmails = emailDao.getEmails()
            emit(Resource.Loading(data = interestingEmails))
            try {
                if (onlineMode) {
                    val emails = getEmails.getEmails()
                    for (i in emails.indices) {

                        val remoteInterestingEmail = ParseEmail().parseEmail(emails[i])
                        if (remoteInterestingEmail != null) {
                            emailDao.insertEmail(remoteInterestingEmail)
                        }
                    }
                    val newInterestingEmails = emailDao.getEmails()
                    emit(Resource.Success(data = newInterestingEmails))
                } else {
                    val sms = GetSMS.getSMS()
                    for (i in sms.indices) {
                        val interestingLink = InterestingEmail(
                            sms.elementAt(i).smsId,
                            sms.elementAt(i).smsTrackLink,
                            sms.elementAt(i).smsAddress,
                            sms.elementAt(i).smsDate,
                            null
                        )
                        emailDao.insertEmail(interestingLink)
                        val newInterestingLink = emailDao.getEmails()
                        emit(Resource.Success(data = newInterestingLink))
                    }
                }
            } catch (e: RuntimeException) {
                emit(
                    Resource.Error(
                        message = "Oops, Somethings Went Wrong",
                        data = interestingEmails
                    )
                )
            } catch (e: java.net.UnknownHostException) {
                emit(
                    Resource.Error(
                        message = "Please Check Your Internet Connection",
                        data = interestingEmails
                    )
                )
            }
        }
    }
}
