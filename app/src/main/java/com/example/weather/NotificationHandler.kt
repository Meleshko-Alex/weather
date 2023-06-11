package com.example.weather

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weather.common.Constants
import com.example.weather.common.SharedPref
import com.example.weather.presentation.splash_screen.SplashScreenActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotificationHandler {

    fun createNotification(context: Context) {
        val notificationIntent = Intent(context, SplashScreenActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT
        )

        createNotificationChannel(context)
        val weatherReport = getNotificationData(context)

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather_1)
            .setContentTitle("Weather Report")
            .setContentText("Here is your forecast")
            .setStyle(NotificationCompat.BigTextStyle().bigText(weatherReport))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(this.javaClass.simpleName, "POST_NOTIFICATIONS permission is not granted")
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        NotificationManagerCompat.from(context).notify(1, notification)
    }

    private fun getNotificationData(context: Context): String {
        val weather = SharedPref(context).getWeatherToday()
        val localDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM.dd.yyyy"))
        return if (localDate != weather.date) {
            "Tap to view today's weather forecast"
        } else {
            weather.summary
        }
    }

    private fun createNotificationChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            Constants.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "This channel send weather reports every N\\A"
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        Log.d(this.javaClass.simpleName, "Notification channel is created")
    }
}