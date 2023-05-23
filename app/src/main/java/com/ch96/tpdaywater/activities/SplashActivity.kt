package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            var pref = getSharedPreferences("User", MODE_PRIVATE)
            var name = pref.getString("name", "")
            var total = pref.getInt("total", 0)
            var alert = pref.getString("alert", "allow")

            if (name != ""){
                GV.name = name!!
                GV.totalWater = total
                GV.alert = alert!!

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, IntroNameActivity::class.java))
                finish()
            }
        }, 2000)
    }
}