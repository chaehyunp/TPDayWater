package com.ch96.tpdaywater

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ch96.tpdaywater.activities.MainActivity
import com.ch96.tpdaywater.activities.SettingActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 0
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "ChannelName"
    }
    override fun onReceive(p0: Context, p1: Intent?) {
        val notificationManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(p0)
        deliverNotification(p0)
    }

    fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, // 채널 아이디
                CHANNEL_NAME, // 채널 이름
                NotificationManager.IMPORTANCE_HIGH
                /*
                1. IMPORTANCE_HIGH = 알림음이 울리고 헤드업 알림으로 표시
                2. IMPORTANCE_DEFAULT = 알림음 울림
                3. IMPORTANCE_LOW = 알림음 없음
                4. IMPORTANCE_MIN = 알림음 없고 상태줄 표시 X
                 */
            )
            notificationChannel.enableLights(true) //불빛
            notificationChannel.lightColor = R.color.main //색상
            notificationChannel.enableVibration(true) //진동
            notificationChannel.description = context.getString(R.string.app_name) //채널 정보
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun deliverNotification(context: Context){
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID, //request code
            contentIntent, //알림클릭시 이동할 인텐트
            PendingIntent.FLAG_UPDATE_CURRENT
            /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
             */
        )

        var goalWater = GV.goalArray[GV.goalArrayNum]
        var havetoWater = (GV.goalArray[GV.goalArrayNum]*1000) - GV.totalWater
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.btn_main) //아이콘
            .setContentTitle("띵동! 물 보충할 시간입니다💦") //제목
            .setContentText("목표치 ${goalWater}L까지 ${havetoWater}ml 남았어요!")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())

    }
}