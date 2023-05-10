package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            var pref = getSharedPreferences("User", MODE_PRIVATE)
            var name = pref.getString("name", "")
            var total = pref.getInt("total", 0)

            if (name != ""){
                GV.name = name!!
                GV.totalWater = total
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, IntroNameActivity::class.java))
                finish()
            }
        }, 2000)
    }
}