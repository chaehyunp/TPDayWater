package com.ch96.tpdaywater

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context, p1: Intent?) {
        val notificationManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val CHANNEL_ID = "channel_id"
        val CHANNEL_NAME = "ChannelName"
        val IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        val NOTIFICATION_ID = 0
        val NOTIFICATION_CHANNEL = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE)

        notificationManager.createNotificationChannel(NOTIFICATION_CHANNEL)

        var goalWater = GV.goalArray[GV.goalArrayNum]
        var havetoWater = (GV.goalArray[GV.goalArrayNum]*1000) - GV.totalWater

        val notificationbuilder = NotificationCompat.Builder(p0, CHANNEL_ID)
            .setSmallIcon(R.drawable.btn_main) //아이콘
            .setContentTitle("띵동! 물 보충할 시간입니다💦") //제목
            .setContentText("목표치 ${goalWater}L까지 ${havetoWater}ml 남았어요!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)


    }

}