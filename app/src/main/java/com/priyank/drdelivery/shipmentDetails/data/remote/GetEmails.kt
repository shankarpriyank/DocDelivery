package com.priyank.drdelivery.shipmentDetails.data.remote

import android.accounts.Account
import android.content.Context
import android.util.Log
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.ListMessagesResponse
import com.google.api.services.gmail.model.Message
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetEmails {
    // Todo: Optimise Later
    suspend fun getEmails(
        applicationContext: Context,
        id: String?,
        email: String?
    ): List<Message> {

        val messageList: MutableList<Message> = mutableListOf()
        lateinit var emailList: Deferred<ListMessagesResponse?>
        val credential = GoogleAccountCredential.usingOAuth2(
            applicationContext, listOf(GmailScopes.GMAIL_READONLY)
        )
            .setBackOff(ExponentialBackOff())
            .setSelectedAccount(
                Account(
                    email, "Dr.Delivery"

                )
            )

        val service = Gmail.Builder(
            NetHttpTransport(), AndroidJsonFactory.getDefaultInstance(), credential
        )
            .setApplicationName("DocDelivery")
            .build()

        val getEmailList = GlobalScope.launch {

            try {
                emailList =
                    async {
                        service.users().messages()?.list(id)?.setQ("subject:shipped")?.execute()
                    }
            } catch (e: UserRecoverableAuthIOException) {
                e.printStackTrace()
            }
        }

        getEmailList.join()

        val job = GlobalScope.launch(Dispatchers.IO) {
            if (emailList.await().toString() != "{\"resultSizeEstimate\":0}") {

                for (i in 0 until emailList.await()?.messages!!.size) {
                    Log.i("Getting Email", "email no $i")
                    val email =
                        async {
                            service.users().messages().get("me", emailList.await()!!.messages[i].id)
                                .setFormat("Full").execute()
                        }
                    messageList.add(email.await())
                }
            }
        }

        job.join()

        return messageList
    }
}
