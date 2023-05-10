package com.ch96.tpdaywater.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ch96.tpdaywater.GV
import com.ch96.tpdaywater.R
import com.ch96.tpdaywater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.tvName.text = "${GV.name}님,"

        binding.tvGoal.text = "목표치 ${GV.goal}L 중에서"
        binding.tvTotal.text = "${GV.totalWater}ml"

        binding.btnAdd.setOnClickListener { addWater() }
        binding.btnCancel.setOnClickListener { cancelWater() }
    }

    fun addWater(){
        GV.totalWater += GV.cup
        binding.tvTotal.text = "${GV.totalWater}ml"

        saveChangedTotal()
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
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}