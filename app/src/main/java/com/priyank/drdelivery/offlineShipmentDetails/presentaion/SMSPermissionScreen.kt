package com.priyank.drdelivery.offlineShipmentDetails.presentaion

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.priyank.drdelivery.navigation.Screen

@SuppressLint("UnrememberedMutableState")
@Composable
fun SMSPermissionScreen(
    onDismiss: () -> Unit,
    navHostController: NavHostController,
    activity: Activity,
    screen2: Boolean,
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
    val count = remember { mutableStateOf(0) }
    val showNewAlert = remember {
        mutableStateOf(false)
    }
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_SMS
        ) -> {
            if (screen2) {
                navHostController.popBackStack()
                navHostController.navigate(Screen.Detail.route)
            } else {
                onDismiss()
                Toast.makeText(
                    context,
                    "SMS permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        else -> {
            if (!showNewAlert.value) {
                AlertDialog(
                    onDismissRequest = { onDismiss() },
                    confirmButton = {
                        TextButton(onClick = {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_SMS
                                ) -> {
                                    if (screen2) {
                                        navHostController.popBackStack()
                                        navHostController.navigate(Screen.Detail.route)
                                    } else {
                                        onDismiss()
                                        Toast.makeText(
                                            context,
                                            "SMS permission granted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                else -> {
                                    // Asking for permission
                                    launcher.launch(Manifest.permission.READ_SMS)

                                    when (PackageManager.PERMISSION_GRANTED) {
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.READ_SMS
                                        ) -> {
                                            if (screen2) {
                                                navHostController.popBackStack()
                                                navHostController.navigate(Screen.Detail.route)
                                            } else {
                                                onDismiss()
                                                Toast.makeText(
                                                    context,
                                                    "SMS permission granted",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                        else -> {
                                            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                                    activity,
                                                    Manifest.permission.READ_SMS
                                                ) && count.value > 0
                                            ) {
                                                // The permission has been permanently denied by the user
                                                showNewAlert.value = true
                                            }
                                            count.value++
                                        }
                                    }
                                }
                            }
                        }) { Text(text = "OK", color = Color(176, 221, 249)) }
                    },
                    dismissButton = {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = "Cancel",
                                color = Color(176, 221, 249)
                            )
                        }
                    },
                    title = { Text(text = "Please confirm") },
                    text = { Text(text = "We are using your SMS details. Do you continue with the requested action?") }
                )
            } else {
                AlertDeniedPer(onDismiss)
            }
        }
    }
}
