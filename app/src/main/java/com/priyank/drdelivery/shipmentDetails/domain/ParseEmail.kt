package com.priyank.drdelivery.shipmentDetails.domain

import android.os.Environment
import android.util.Log
import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class ParseEmail {

    // #TODO
    // Add more pattern to find link from Amazon,Myntra,Ajio etc
    // Also refactor the code to make so that new pattern can be added easily in future...

    private fun getTextFromBodyPart(
        bodyPart: MessagePart
    ): String {

        var result: String = ""
        if (bodyPart.mimeType == "text/plain") {
            val r = bodyPart.body.decodeData()
            result = r.toString(Charsets.UTF_8)
        } else if (bodyPart.mimeType == "text/html") {
            val html = bodyPart.body.decodeData()
            result = html.toString(Charsets.UTF_8)
        }
        return result
    }

    fun parseEmail(email: Message): InterestingEmail? {
        //   val emailSize = email.payload.parts.size
        var parsedEmail = " "

//        for (k in 0 until emailSize) {
//            parsedEmail += getTextFromBodyPart(email.payload.parts[k])
//        }
        parsedEmail += stringFromEmail(email)

        val link = ""
        val FlipkartPattern =
            Regex("http:\\/\\/delivery\\..+?\\.flipkart\\.com\\/([A-Za-z0-9\\?\\=&\\/\\\\\\+]+)")
        val isLinkpresent = FlipkartPattern.containsMatchIn(parsedEmail)

        if (isLinkpresent) {

            Log.d("EMAIL ${timeEmailWasRecieved(email)}", parsedEmail)

            return InterestingEmail(FlipkartPattern.find(parsedEmail)!!.value, "Flipkart", timeEmailWasRecieved(email), null)
        } else {

            Log.d(" LINK NOT FOUND IN EMAIL ${timeEmailWasRecieved(email)}", parsedEmail)

            return null
        }
    }

    fun timeEmailWasRecieved(email: Message): String {

        val timeInMillis = email.get("internalDate") as String
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(timeInMillis.toLong()).toLocaleString()
        return netDate
    }
    fun stringFromEmail(email: Message): String {
        var body = ""

        val re = email.raw
        Log.e("FULL RAW EMAIL", re)
        val path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
        val fileOutputStream: FileOutputStream = File(path, "rawem_${email.id}.txt").outputStream()
        val outputWriter = OutputStreamWriter(fileOutputStream)
        outputWriter.write(re)
        outputWriter.write("/n")
        outputWriter.close()
        val decoder: Base64.Decoder = Base64.getDecoder()
        val gg = String(decoder.decode(re))
        body = gg
        return body
    }
}
