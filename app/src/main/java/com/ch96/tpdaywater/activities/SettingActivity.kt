package com.ch96.tpdaywater.activities

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.ch96.tpdaywater.AlarmReceiver
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivitySettingBinding
import java.util.Calendar

internal var alarmManager:AlarmManager ?= null

class SettingActivity : AppCompatActivity() {

    val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    
    var minHour = 7
    var maxHour = 21
    var alertTerm = 1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_arrow_back)

        binding.tvName.text = GV.name

        binding.tvCup.text = "${GV.cup}ml"
        binding.tvGoal.text = "${GV.goalArray[GV.goalArrayNum]}L"

        var pref = getSharedPreferences("User", MODE_PRIVATE)
        var alert = pref.getString("alert", "allow")
        binding.switchAlert.isChecked = alert == "allow"

        binding.switchAlert.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                alertAllow()
            } else {
                alertDeny()
            }

        }
        
        binding.tvTime
        binding.tvAlertTerm

        //sharedPref로 저장후 불러오기
//        var minHour = 7
//        var maxhour = 21
//        var alertTerm = 1
    }

    fun isNotificationTime(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        // 오전 7시부터 오후 9시까지 알림을 보내기 위한 조건문
        return hour >= minHour && hour < maxHour
    }

    fun alertAllow(){
        binding.layoutAlertTimeDe.visibility = View.INVISIBLE
        binding.layoutAlertTermDe.visibility = View.INVISIBLE

        val receiverIntent = Intent(application, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(application, 0, receiverIntent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager?.cancel(pendingIntent)

    }

    fun alertDeny(){
        binding.layoutAlertTimeDe.visibility = View.VISIBLE
        binding.layoutAlertTermDe.visibility = View.VISIBLE

        val receiverIntent = Intent(application, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(application, 0, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager?.cancel(pendingIntent)
    }

    fun saveAlert(){

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}