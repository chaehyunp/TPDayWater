package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ch96.tpdaywater.GV
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
        var name = binding.etName.text.toString()
        if (name != "") {
            var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
            editor.putString("name", name).commit()
            GV.name = name
            startActivity(Intent(this, IntroGoalActivity::class.java))
        } else Toast.makeText(this, "이름을 입력해주세요!", Toast.LENGTH_SHORT).show()
    }
}