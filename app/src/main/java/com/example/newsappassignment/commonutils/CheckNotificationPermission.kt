package com.example.newsappassignment.commonutils

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Checks if notifications are enabled for the app.
 * @param context The context used to access the NotificationManager service.
 * @return True if notifications are enabled, false otherwise.
 */
@RequiresApi(Build.VERSION_CODES.N)
fun isNotificationsEnabled(context: Context): Boolean {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return notificationManager.areNotificationsEnabled()
}