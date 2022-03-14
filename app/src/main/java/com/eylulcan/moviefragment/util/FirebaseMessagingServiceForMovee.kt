package com.eylulcan.moviefragment.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.eylulcan.moviefragment.MainActivity
import com.eylulcan.moviefragment.R
import com.eylulcan.moviefragment.data.Utils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Math.abs
import java.util.*

private const val CHANNEL_ID = "com.eylulcan.moviefragment"
private const val TITLE = "MoveeApp"

class FirebaseMessagingServiceForMovee : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            val title = it.title ?: TITLE
            val message = it.body ?: Utils.IF_STR_NULL
            sendNotification(title, message)
        }
    }

    private fun sendNotification(title: String, message: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

        intent.putExtra("notification",true)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val requestCode = abs(Random().nextInt())

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.movie)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
            if (title.isNotEmpty()) {
                notification.setContentTitle(title)
            }
            val notificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(mChannel)
            notificationManager.notify(requestCode, notification.build())
        }
        else {
            val notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.movie)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)

            notification.setContentTitle(title)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(requestCode, notification.build())
        }


        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.movie)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setVibrate(longArrayOf(1000, 1000))
            .setContentIntent(pendingIntent)
        notificationBuilder.setContentTitle(title)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}