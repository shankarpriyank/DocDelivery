package com.priyank.drdelivery.navigation

sealed class Screen(val route: String) {

    object Authentication : Screen(route = "authentication")
    object ShipmentDetail : Screen(route = "shipment Detail")
}
