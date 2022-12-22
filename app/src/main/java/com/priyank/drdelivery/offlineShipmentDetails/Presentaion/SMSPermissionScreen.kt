package com.priyank.drdelivery.shipmentDetailsOffline

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priyank.drdelivery.offlineShipmentDetails.Data.GetSMS.getSMS
import com.priyank.drdelivery.offlineShipmentDetails.Domain.SMSViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SMSPermissionScreen(onDismiss: () -> Unit, smsViewModel: SMSViewModel = viewModel()) {
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
                        CoroutineScope(Dispatchers.IO).launch {
                            smsViewModel.smsList = getSMS(context)
                        }
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
