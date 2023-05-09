package com.ch96.tpdaywater.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch96.tpdaywater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}