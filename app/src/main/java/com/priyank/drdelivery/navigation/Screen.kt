package com.priyank.drdelivery.navigation

sealed class Screen(val route: String) {
    object Authentication : Screen(route = "authentication")
    object Detail : Screen(route = "Detail")
    object Welcome : Screen(route = "welcome")
}
