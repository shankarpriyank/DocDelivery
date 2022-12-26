package com.priyank.drdelivery.shipmentDetailsOffline

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.priyank.drdelivery.navigation.Screen

@Composable
fun SMSPermissionScreen(
    onDismiss: () -> Unit,
    navHostController: NavHostController
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.i("ExampleScreen", "PERMISSION GRANTED")
        } else {
            Log.i("ExampleScreen", "PERMISSION DENIED")
        }
    }
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_SMS
                    ) -> {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Detail.route)
                    }

                    else -> {
                        // Asking for permission
                        launcher.launch(Manifest.permission.READ_SMS)
                    }
                }
            }) { Text(text = "OK", color = Color(176, 221, 249)) }
        },
        dismissButton = {
            TextButton(onClick = {}) { Text(text = "Cancel", color = Color(176, 221, 249)) }
        },
        title = { Text(text = "Please confirm") },
        text = { Text(text = "We are using your SMS details. Do you continue with the requested action?") }
    )
}
