package com.hoshiyomi.payloadtoolkit

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

/**
 * RepackNotificationHelper — Centralized notification management for repack operations.
 *
 * Extracted from MainActivity companion object to reduce monolithic file size.
 * All methods are static and safe to call from any context (including destroyed Activity).
 */
object RepackNotificationHelper {

    private const val NOTIFICATION_ID = 1001

    @Volatile
    var appContext: Context? = null

    /** Show ongoing progress notification with determinate progress bar. */
    fun showProgress(message: String, percent: Int) {
        val ctx = appContext ?: return
        try {
            val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = ctx.packageManager.getLaunchIntentForPackage(ctx.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            } ?: return
            val pi = PendingIntent.getActivity(
                ctx, 0, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification = NotificationCompat.Builder(ctx, PayloadToolkitApp.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Payload Toolkit")
                .setContentText(message)
                .setProgress(100, percent.coerceIn(0, 100), percent == 0)
                .setOngoing(true)
                .setSilent(true)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            nm.notify(NOTIFICATION_ID, notification)
        } catch (_: Exception) { /* notification is non-critical */ }
    }

    /** Show completion/failure notification (auto-dismissable). */
    fun showCompletion(success: Boolean, message: String) {
        val ctx = appContext ?: return
        try {
            val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = ctx.packageManager.getLaunchIntentForPackage(ctx.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            } ?: return
            val pi = PendingIntent.getActivity(
                ctx, 0, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification = NotificationCompat.Builder(ctx, PayloadToolkitApp.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle(if (success) "Repack Completed" else "Repack Failed")
                .setContentText(message)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            nm.notify(NOTIFICATION_ID, notification)
        } catch (_: Exception) { /* notification is non-critical */ }
    }

    /** Cancel the repack notification. */
    fun cancel() {
        try {
            appContext?.let {
                (it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .cancel(NOTIFICATION_ID)
            }
        } catch (_: Exception) { /* notification is non-critical */ }
        appContext = null
    }
}
