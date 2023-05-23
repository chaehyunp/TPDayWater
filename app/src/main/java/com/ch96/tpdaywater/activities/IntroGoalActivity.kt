package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivityIntroGoalBinding

class IntroGoalActivity : AppCompatActivity() {

    val binding by lazy { ActivityIntroGoalBinding.inflate(layoutInflater) }
    val goal = arrayOf(
        "1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9",
        "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "2.9", "3.0"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOkay.setOnClickListener { clickBtnOkay() }
        binding.numberPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            GV.goalArrayNum = i2
        }

        binding.numberPicker.wrapSelectorWheel = false
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 20
        binding.numberPicker.value = 8

        binding.numberPicker.displayedValues = goal
    }

    fun clickBtnOkay(){
        startActivity(Intent(this, IntroAlertActivity::class.java))
    }
}

