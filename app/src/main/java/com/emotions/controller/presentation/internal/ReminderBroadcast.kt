package com.emotions.controller.presentation.internal

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.emotions.controller.R
import com.emotions.controller.presentation.ui.home.HomeActivity


class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(it, "channelCreateEmotion")
            builder.setSmallIcon(R.drawable.ic_tabs_emotion)
                .setContentTitle(it.getString(R.string.notification_title))
                .setContentText(it.getString(R.string.notification_text))
                .priority = NotificationCompat.PRIORITY_DEFAULT

            val intentHomeActivity = Intent(context, HomeActivity::class.java)
            intentHomeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intentHomeActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            builder.setContentIntent(
                PendingIntent.getActivity(
                    it, 0,
                    intentHomeActivity, PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(101, builder.build())
        }
    }
}