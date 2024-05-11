package com.example.newsappassignment.commonutils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog

/**
 * Displays a dialog to ask the user for notification permission.
 * @param context The context in which the dialog should be displayed.
 */
fun showNotificationDialog(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Notification Permission")
    builder.setMessage("Would you like to receive notifications from this app?")

    // Positive button to request notification permission
    builder.setPositiveButton("Yes") { dialog, _ ->
        requestNotificationPermission(context)
        dialog.dismiss()
    }

    // Negative button to dismiss the dialog
    builder.setNegativeButton("No") { dialog, _ ->
        dialog.dismiss()
    }

    // Create and display the dialog
    val dialog = builder.create()
    dialog.show()
}

/**
 * Requests notification permission by opening the app notification settings.
 * @param context The context used to start the settings activity.
 */
private fun requestNotificationPermission(context: Context) {
    // Create an intent to open the app notification settings
    val intent = Intent()
    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
    intent.putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
    // Start the settings activity
    context.startActivity(intent)
}
