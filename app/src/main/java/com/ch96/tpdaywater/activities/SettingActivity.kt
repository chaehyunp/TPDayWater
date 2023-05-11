package com.ch96.tpdaywater.activities

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.ch96.tpdaywater.AlarmReceiver
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivitySettingBinding
import java.util.Calendar

internal var alarmManager:AlarmManager ?= null

class SettingActivity : AppCompatActivity() {

    val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    val alarmManager by lazy { getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    var alert = "allow"
    
    var minHour = 7
    var maxHour = 21
    var alertTerm = 1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_arrow_back)

        //알림설정
//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 1000 * 60 * 60, 1000 * 60 * 60, pendingIntent)
//
//        var time = System.currentTimeMillis()+ 1000 * 60 * 60
//        Log.i("whatis","$time")

        binding.tvName.text = GV.name

        binding.tvName.setOnClickListener { changeName() }

        binding.tvCup.text = "${GV.cup}ml"
        binding.tvGoal.text = "${GV.goalArray[GV.goalArrayNum]}L"

        binding.tvCup.setOnClickListener { changeCup() }
        binding.tvGoal.setOnClickListener { changeGoal() }

        getAlert()
        binding.switchAlert.isChecked = alert == "allow"

        binding.switchAlert.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                alertAllow()
            } else {
                alertDeny()
            }
        }

        getMinHour()
        getMaxHour()
        getTerm()
        
        binding.tvMin.text = "${minHour}시부터 "
        binding.tvMax.text = "${maxHour}시까지"
        binding.tvAlertTerm.text = "${alertTerm}시간"

        binding.tvMinDe.text = binding.tvMin.text
        binding.tvMaxDe.text = binding.tvMax.text
        binding.tvAlertTermDe.text = binding.tvAlertTerm.text

        binding.tvMin.setOnClickListener { changetime() }
        binding.tvMax.setOnClickListener { changetime() }
        binding.tvAlertTerm.setOnClickListener { changeTerm() }
    }

    fun changeName(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_name, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)

        val changeNameDialog = builder.show()
        val btnOkay = dialogView.findViewById<TextView>(R.id.btn_okay)

        btnOkay.setOnClickListener {

            var name = binding.tvName.text.toString()
            GV.name = name
            binding.tvName.text = GV.name

            changeNameDialog.dismiss()
        }
    }

    fun changeCup(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_cup, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)

        val changeTermDialog = builder.show()

        val cupPicker = dialogView.findViewById<NumberPicker>(R.id.number_picker)
        val btnOkay = dialogView.findViewById<TextView>(R.id.btn_okay)

        cupPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            alertTerm = i2
        }

        cupPicker.wrapSelectorWheel = false
        cupPicker.minValue = 1
        cupPicker.maxValue = 4
        cupPicker.value = alertTerm

        btnOkay.setOnClickListener {
            binding.tvAlertTerm.text = "${alertTerm}ml"

            binding.tvAlertTermDe.text = binding.tvAlertTerm.text

            saveTerm()
            changeTermDialog.dismiss()
        }
    }

    fun changeGoal(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_cup, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)

        val changeTermDialog = builder.show()

        val cupPicker = dialogView.findViewById<NumberPicker>(R.id.number_picker)
        val btnOkay = dialogView.findViewById<TextView>(R.id.btn_okay)

        cupPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            alertTerm = i2
        }

        cupPicker.wrapSelectorWheel = false
        cupPicker.minValue = 1
        cupPicker.maxValue = 4
        cupPicker.value = alertTerm

        btnOkay.setOnClickListener {
            binding.tvAlertTerm.text = "${alertTerm}ml"

            binding.tvAlertTermDe.text = binding.tvAlertTerm.text

            saveTerm()
            changeTermDialog.dismiss()
        }
    }

    fun changetime(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)

        val changeTimeDialog = builder.show()

        val minPicker = dialogView.findViewById<NumberPicker>(R.id.min_picker)
        val maxPicker = dialogView.findViewById<NumberPicker>(R.id.max_picker)
        val btnOkay = dialogView.findViewById<TextView>(R.id.btn_okay)

        minPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            minHour = i2
        }

        minPicker.wrapSelectorWheel = false
        minPicker.minValue = 0
        minPicker.maxValue = 23
        minPicker.value = minHour

        maxPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            maxHour = i2
        }

        maxPicker.wrapSelectorWheel = false
        maxPicker.minValue = 0
        maxPicker.maxValue = 23
        maxPicker.value = maxHour

        btnOkay.setOnClickListener {
            binding.tvMin.text = "${minHour}시부터 "
            binding.tvMax.text = "${maxHour}시까지"

            binding.tvMinDe.text = binding.tvMin.text
            binding.tvMaxDe.text = binding.tvMax.text

            saveMinHour()
            saveMaxHour()
            changeTimeDialog.dismiss()
        }
    }

    fun changeTerm(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_term, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)

        val changeTermDialog = builder.show()

        val termPicker = dialogView.findViewById<NumberPicker>(R.id.term_picker)
        val btnOkay = dialogView.findViewById<TextView>(R.id.btn_okay)

        termPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            alertTerm = i2
        }

        termPicker.wrapSelectorWheel = false
        termPicker.minValue = 1
        termPicker.maxValue = 4
        termPicker.value = alertTerm

        btnOkay.setOnClickListener {
            binding.tvAlertTerm.text = "${alertTerm}시간"

            binding.tvAlertTermDe.text = binding.tvAlertTerm.text

            saveTerm()
            changeTermDialog.dismiss()
        }
    }

//    fun isNotificationTime(): Boolean {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        // 오전 7시부터 오후 9시까지 알림을 보내기 위한 조건문
//        return hour >= minHour && hour < maxHour
//    }

    fun alertAllow(){
        binding.layoutAlertTimeDe.visibility = View.INVISIBLE
        binding.layoutAlertTermDe.visibility = View.INVISIBLE
        binding.layoutAlertTime.visibility = View.VISIBLE
        binding.layoutAlertTerm.visibility = View.VISIBLE

        GV.alert = "allow"
        saveAlert()


//        val receiverIntent = Intent(application, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(application, 0, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
//        alarmManager?.cancel(pendingIntent)

    }

    fun alertDeny(){
        binding.layoutAlertTimeDe.visibility = View.VISIBLE
        binding.layoutAlertTermDe.visibility = View.VISIBLE
        binding.layoutAlertTime.visibility = View.INVISIBLE
        binding.layoutAlertTerm.visibility = View.INVISIBLE

        GV.alert = "deny"
        saveAlert()

//        val receiverIntent = Intent(application, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(application, 0, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
//        alarmManager?.cancel(pendingIntent)
    }

    fun saveAlert(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putString("alert", GV.alert).commit()
    }

    fun saveMinHour(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putInt("min", minHour).commit()
    }

    fun saveMaxHour(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putInt("max", maxHour).commit()
    }

    fun saveTerm(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putInt("term", alertTerm).commit()
    }

    fun getAlert(){
        var pref = getSharedPreferences("User", MODE_PRIVATE)
        var alert = pref.getString("alert", "allow")
    }

    fun getMinHour(){
        var pref = getSharedPreferences("User", MODE_PRIVATE)
        minHour = pref.getInt("min", 8)
    }

    fun getMaxHour(){
        var pref = getSharedPreferences("User", MODE_PRIVATE)
        maxHour = pref.getInt("max", 21)
    }

    fun getTerm(){
        var pref = getSharedPreferences("User", MODE_PRIVATE)
        alertTerm = pref.getInt("term", 1)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}