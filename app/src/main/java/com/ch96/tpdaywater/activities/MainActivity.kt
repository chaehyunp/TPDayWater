package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.tvName.text = "${GV.name}님,"

        binding.tvGoal.text = "목표치 ${GV.goalArray[GV.goalArrayNum]}L 중에서"
        binding.tvTotal.text = "${GV.totalWater}ml"

        saveProgress()

        binding.btnAdd.setOnClickListener { addWater() }
        binding.btnCancel.setOnClickListener { cancelWater() }
    }

    fun addWater(){
        GV.totalWater += GV.cup
        binding.tvTotal.text = "${GV.totalWater}ml"

        saveChangedTotal()
        saveProgress()
    }

    fun cancelWater(){
        if (GV.totalWater >= GV.cup) {
            GV.totalWater -= GV.cup
            binding.tvTotal.text = "${GV.totalWater}ml"
        } else {
            GV.totalWater = 0
            binding.tvTotal.text = "${GV.totalWater}ml"
        }
        saveChangedTotal()
        saveProgress()
    }

    fun saveProgress(){
        if(GV.totalWater != 0){
            var proceed = GV.totalWater/(GV.goalArray[GV.goalArrayNum]*1000)
            var percent = (proceed*100).toInt()
            binding.progressbar.setProgress(percent)

        } else binding.progressbar.setProgress(0)
    }

    fun saveChangedTotal(){
        var editor = getSharedPreferences("User", MODE_PRIVATE).edit()
        editor.putInt("total", GV.totalWater).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_setting -> {
                var intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}