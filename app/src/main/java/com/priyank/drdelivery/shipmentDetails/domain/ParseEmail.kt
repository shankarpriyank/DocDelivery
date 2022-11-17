package com.priyank.drdelivery.shipmentDetails.domain

import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart

class ParseEmail() {

    fun parseEmail(email: Message): String {
        val emailSize = email.payload.parts.size
        var parsedEmail = " "
        for (k in 0 until emailSize) {
            parsedEmail += getTextFromBodyPart(email.payload.parts[k])
        }
        return parsedEmail
    }

    private fun getTextFromBodyPart(
        bodyPart: MessagePart
    ): String {
        var result: String = ""
        if (bodyPart.mimeType == "text/plain") {
            val r = bodyPart.body.decodeData()
            result = String(r)
        } else if (bodyPart.mimeType == "text/html") {
            val html = bodyPart.body.decodeData()
            result = String(html)
        }
        return result
    }
}
