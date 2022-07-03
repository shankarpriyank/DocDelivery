package com.priyank.drdelivery.authentication.model

import androidx.lifecycle.ViewModel
import com.priyank.drdelivery.R

class LoginViewModel : ViewModel() {
    fun data(): List<Info> {

        return listOf(
            Info(
                "Auto-track orders with \n email sync", R.drawable.ic_autotrack
            ),
            Info(
                "Get Realtime Delivery Updates", R.drawable.ic_realtime
            ),
            Info(
                "Your Data is safe, we do everything on the device itself",
                R.drawable.ic_privacy_png
            )

        )
    }
}
