package com.priyank.drdelivery.offlineShipmentDetails.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.priyank.drdelivery.offlineShipmentDetails.domain.model.RequiredSMS
import java.text.DateFormat.getDateTimeInstance
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

class GetSMS(private val context: Context) {
    private val smsList = mutableSetOf<RequiredSMS>()

    fun getSMS(): MutableSet<RequiredSMS> {
        // Some works that require permission
        val contentResolver = context.contentResolver
        val smsUri = Uri.parse("content://sms/")
        val projection = arrayOf("_id", "address", "date", "body")
        val selection = "address = ?"
        val selectionArgs = arrayOf("AD-FLPKRT")
        val sortOrder = "date DESC"
        val cursor = contentResolver.query(smsUri, projection, selection, selectionArgs, sortOrder)

        // Iterate over the cursor and print out the SMS messages
        while (cursor!!.moveToNext()) {
            val id = cursor.getString(0)
            val address = cursor.getString(1)
            val body = cursor.getString(3)
            val date = cursor.getLong(2)
            val date2 = Date(date)
            // val format = SimpleDateFormat("dd/MM/yy HH:mm:ss")
            val format = getDateTimeInstance()
            val dateText: String = format.format(date2)
            val pattern: Pattern = Pattern.compile("http://fkrt.it/Zy[a-zA-Z][a-zA-Z][a-zA-Z][a-zA-Z]uuuN")
            val matcher: Matcher = pattern.matcher(body)
            var link = "Not found"
            if (matcher.find()) {
                // Retrieve the matched string
                link = matcher.group()
            }
            smsList.add(RequiredSMS(id, address, dateText, body, link))
            Log.i(
                "Getting SMS",
                "SMS message: id=$id, date=$dateText, address=$address,body=$body, link=$link"
            )
        }
        // Remember to close the cursor when you are done
        cursor.close()
        Log.i("ExampleScreen", "Iterated all SMS")
        return smsList
    }
}
