package com.priyank.drdelivery.shipmentDetails.domain

import android.util.Log
import com.google.api.client.util.Base64
import com.google.api.client.util.StringUtils
import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import java.util.Date

class ParseEmail() {

    // #TODO
    // Add more pattern to find link from Amazon,Myntra,Ajio etc
    // Also refactor the code to make so that new pattern can be added easily in future...

    private fun getTextFromBodyPart(
        bodyPart: MessagePart
    ): String {

        var result: String = ""
        if (bodyPart.mimeType == "text/plain") {

            val r = bodyPart.body.data
            result =
                StringUtils
                    .newStringUtf8(Base64.decodeBase64(r))
        } else if (bodyPart.mimeType == "text/html") {
            val html = bodyPart.body.data
            val s = StringUtils
                .newStringUtf8(Base64.decodeBase64(html))

            result = s
        }

        return result
    }

    fun parseEmail(email: Message): InterestingEmail? {

        val emailSize = email.payload.parts?.size ?: 0
        var parsedEmail = " "

        for (k in 0 until emailSize) {
            parsedEmail += getTextFromBodyPart(email.payload.parts[k])
        }

        val FlipkartPattern =
            Regex("http:\\/\\/delivery\\..+?\\.flipkart\\.com\\/([A-Za-z0-9\\?\\=&\\/\\\\\\+]+)")
        val isLinkpresent = FlipkartPattern.containsMatchIn(parsedEmail)

        if (isLinkpresent) {

            Log.d("EMAIL ${timeEmailWasRecieved(email)}", parsedEmail)

            return InterestingEmail(
                email.id,
                FlipkartPattern.find(parsedEmail)!!.value,
                "Flipkart",
                timeEmailWasRecieved(email),
                null
            )
        } else {

            Log.d(" LINK NOT FOUND IN EMAIL ${timeEmailWasRecieved(email)}", parsedEmail)

            return null
        }
    }

    fun timeEmailWasRecieved(email: Message): String {

        val timeInMillis = email.get("internalDate") as String
        val netDate = Date(timeInMillis.toLong()).toLocaleString()
        return netDate
    }
}
