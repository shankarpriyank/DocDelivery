package com.priyank.drdelivery.shipmentDetails.data.repository

import android.util.Log
import com.priyank.drdelivery.shipmentDetails.data.local.InteresingEmailDao
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import com.priyank.drdelivery.shipmentDetails.domain.repository.EmailRepository
import com.priyank.drdelivery.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.http.HttpException
import java.io.IOException

class EmailRepositoryImpl(
    private val emailDao: InteresingEmailDao,
    private val getEmails: GetEmails
) : EmailRepository {
    override fun getEmails(): Flow<Resource<List<InterestingEmail>>> = flow {
        Log.e("Hello", "J")
        emit(Resource.Loading())
        val interestingEmails = emailDao.getEmails()
        emit(Resource.Loading(data = interestingEmails))
        try {
            val emails = getEmails.getEmails()
            for (i in emails.indices) {
                val remoteInterestingEmail = ParseEmail().parseEmail(emails[i])
                if (remoteInterestingEmail != null) {
                    emailDao.insertEmail(remoteInterestingEmail)
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, Somethings Went Wrong", data = interestingEmails))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Please Check Your Internet Connection",
                    data = interestingEmails
                )
            )
        }
        val newInterestingEmails = emailDao.getEmails()
        emit(Resource.Success(data = newInterestingEmails))
    }
}
