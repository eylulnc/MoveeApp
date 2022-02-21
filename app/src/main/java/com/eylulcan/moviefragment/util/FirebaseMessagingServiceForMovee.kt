package com.eylulcan.moviefragment.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.eylulcan.moviefragment.MainActivity
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.data.Utils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val CHANNEL_ID = "com.eylulcan.moviefragment"

open class FirebaseMessagingServiceForMovee : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            val title = it.title ?: Utils.IF_STR_NULL
            val message = it.body ?: Utils.IF_STR_NULL
            generateNotification(title, message)
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.movie)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder.setCustomContentView(getNotificationView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, getString(R.string.title),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }

        notificationManager.notify(0, builder.build())
    }

    private fun getNotificationView(title: String, message: String): RemoteViews {

        val remoteViews = RemoteViews(getString(R.string.app_package_name), R.layout.notification)
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        //remoteView.setImageViewResources(R.id.app_logo, R.drawable.movie)
        return remoteViews
    }
}