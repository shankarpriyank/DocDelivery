package com.priyank.drdelivery.offlineShipmentDetails.Presentaion

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.priyank.drdelivery.R

@Composable
fun AlertDeniedPer(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        title = { Text(context.getString(R.string.new_alert_box_title)) },
        text = { Text(context.getString(R.string.new_alert_box_content)) },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) { Text(text = "Cancel", color = Color(176, 221, 249)) }
        },
        confirmButton = { TextButton(onClick = { AppSettings(context) }) { Text(text = "Settings", color = Color(176, 221, 249)) } }

    )
}
fun AppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", "com.priyank.drdelivery", null)
    }
    startActivity(context, intent, null)
}
