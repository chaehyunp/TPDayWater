package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivityIntroNameBinding

class IntroNameActivity : AppCompatActivity() {

    val binding by lazy { ActivityIntroNameBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOkay.setOnClickListener { clickBtnOkay() }
    }

    fun clickBtnOkay(){
        startActivity(Intent(this, IntroGoalActivity::class.java))
    }
}