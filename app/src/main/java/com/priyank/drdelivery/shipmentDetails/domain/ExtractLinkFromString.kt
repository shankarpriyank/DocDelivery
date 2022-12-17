package com.priyank.drdelivery.shipmentDetails.domain

class ExtractLinkFromString {

    // #TODO
    // Add more pattern to find link from Amazon,Myntra,Ajio etc
    // Also refactor the code to make so that new pattern can be added easily in future...

    fun findLink(email: String): Triple<Boolean, String, String> {
        val link = ""
//        val pattern =
//            Regex("http:\\/\\/delivery\\..+?\\.flipkart\\.com\\/([A-Za-z0-9\\?\\=&\\/\\\\\\+]+)")
        val pattern = Regex("http://delivery.flipkart.com/abcd")
        val isLinkpresent = pattern.containsMatchIn(email)
        return if (isLinkpresent) {
            Triple(true, pattern.find(email)!!.value, "Flipkart")
        } else {
            Triple(false, link, "N/A")
        }
    }
}
