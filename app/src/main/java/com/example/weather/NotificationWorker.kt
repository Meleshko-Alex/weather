package com.example.weather

import android.content.Context
import android.icu.util.LocaleData
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationWorker (
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        NotificationHandler().createNotification(context)
        return Result.success()
    }

    companion object {

        fun schedule(context: Context) {
            Log.d("NotificationWorker", "schedule is called")
            val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .setRequiresDeviceIdle(false)
                .build()

            val now = Calendar.getInstance()
            val target = (Calendar.getInstance()).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
            }

            if (target.before(now)) {
                target.add(Calendar.DAY_OF_YEAR, 1)
            }

            // for testing purposes to receive a notification every 15 min use this
//            val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
//                .addTag("TAG REMINDER WORKER")
//                .setInitialDelay(10_000L, TimeUnit.MILLISECONDS)
//                .setConstraints(constraints)
//                .build()

            val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
                .addTag("TAG REMINDER WORKER")
                .setInitialDelay(target.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "notifications",
                ExistingPeriodicWorkPolicy.KEEP,
                notificationRequest
            )

            // test
            val target1 = Calendar.getInstance()
            target1.set(Calendar.MINUTE, 0)
            target1.add(Calendar.HOUR_OF_DAY, 1)

            val notificationRequest1 = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
                .addTag("TAG REMINDER WORKER 1H")
                .setInitialDelay(target.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "notifications_every_hour",
                ExistingPeriodicWorkPolicy.KEEP,
                notificationRequest1
            )

            Log.d("NotificationWorker", "The work is scheduled")
        }
    }
}