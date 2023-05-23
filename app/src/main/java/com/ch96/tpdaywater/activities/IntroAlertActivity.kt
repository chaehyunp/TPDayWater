package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivityIntroAlertBinding

class IntroAlertActivity : AppCompatActivity() {

    val binding by lazy { ActivityIntroAlertBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnYes.setOnClickListener { clickBtnYes() }
        binding.btnNo.setOnClickListener { clickBtnNo() }
    }

    fun clickBtnYes(){
        //초기설정 액티비티 지우고 메인으로 이동
        val intent = Intent(this, MainActivity::class.java)
        GV.alert = "allow"
        saveAlert()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun clickBtnNo(){
        val intent = Intent(this, MainActivity::class.java)
        GV.alert = "deny"
        saveAlert()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun saveAlert(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putString("alert", GV.alert).commit()
    }
}