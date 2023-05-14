package com.ch96.tpdaywater

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.Calendar

class NotiReceiver :BroadcastReceiver() {
    val CHANNEL_ID = "channel_id"
    val NOTIFICATION_ID = 1001

    @SuppressLint("ScheduleExactAlarm")
    override fun onReceive(p0: Context, p1: Intent) {
        val notificationManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)

        var min = p1.getIntExtra("min", 0)
        var max = p1.getIntExtra("max", 0)
        var term = p1.getIntExtra("term", 0)

        val goalWater = GV.goalArray[GV.goalArrayNum]
        val havetoWater = ((GV.goalArray[GV.goalArrayNum]*1000) - GV.totalWater) as Int

//        val current = min + (Math.random() * (max - min + 1)).toInt()
        val builder = NotificationCompat.Builder(p0, CHANNEL_ID)
            .setSmallIcon(R.drawable.btn_main) //아이콘
            .setContentTitle("띵동! 물 보충할 시간입니다💦") //제목
            .setContentText("목표치 ${goalWater}L까지 ${havetoWater}ml 남았어요!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, term)

        val notiIntent = Intent(p0, NotiReceiver::class.java)
        notiIntent.putExtra("min", min)
        notiIntent.putExtra("max", max)
        notiIntent.putExtra("term", term)


        val pendingIntent = PendingIntent.getBroadcast(
            p0, 0, notiIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = p0.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

    fun createNotificationChannel(notificationManager: NotificationManager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val CHANNEL_NAME = "ChannelName"
            val CHANNEL_DESCRIPTION = "Channel Description"
            val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
            val NOTIFICATION_CHANNEL = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE).apply {
                description = CHANNEL_DESCRIPTION
            }
            notificationManager.createNotificationChannel(NOTIFICATION_CHANNEL)
        }
    }
}