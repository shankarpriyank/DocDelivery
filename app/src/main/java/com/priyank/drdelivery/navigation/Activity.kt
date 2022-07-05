package com.priyank.drdelivery.navigation

sealed class Activity(val route: String) {

    object Authentication : Activity(route = "authentication")
    object ShipmentDetail : Activity(route = "shipment Detail")
}
