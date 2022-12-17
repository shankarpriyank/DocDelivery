package com.priyank.drdelivery.shipmentDetailsOffline

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat

@Composable
fun SMSPermissionScreen(onDismiss: () -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.i("ExampleScreen", "PERMISSION GRANTED")
        } else {
            // Permission Denied: Do something
            Log.i("ExampleScreen", "PERMISSION DENIED")
        }
    }
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismiss() },
//           properties = DialogProperties(
//               usePlatformDefaultWidth = false
//           )
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            onClick = {
                // Check permission
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_SMS
                    ) -> {
                        // Some works that require permission
                        val contentResolver = context.contentResolver
                        val smsUri = Uri.parse("content://sms/")
                        val projection = arrayOf("_id", "address", "body")
                        val cursor = contentResolver.query(smsUri, projection, null, null, null)

                        // Iterate over the cursor and print out the SMS messages
                        while (cursor!!.moveToNext()) {
                            val id = cursor?.getInt(0)
                            val address = cursor?.getString(1)
                            val body = cursor?.getString(2)
                            Log.i(
                                "Getting SMS",
                                "SMS message: id=$id, address=$address, body=$body"
                            )
                        }
                        // Remember to close the cursor when you are done
                        cursor.close()
                        Log.i("ExampleScreen", "Code requires permission")
                    }
                    else -> {
                        // Asking for permission
                        launcher.launch(Manifest.permission.READ_SMS)
                    }
                }
            }
        ) {
            Text(text = "Check and Request Permission")
        }
    }
}
