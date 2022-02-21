package com.eylulcan.moviefragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


private const val CHANNEL_ID = "com.eylulcan.moviefragment"
private const val NOTIFICATION_ID = 101

private const val TITLE = "Check This Out!"
private const val DESC = "Can\'t find a movie to watch check most popular movie section"

class Notification: BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_videocam)
            .setContentTitle(TITLE)
            .setContentText(DESC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
