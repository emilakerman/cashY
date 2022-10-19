package com.example.cashy


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.expandablerecycleviewkotlin.SettingAdapter


class SettingsActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    val settingList = ArrayList<Settings>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val settingAdapter = SettingAdapter(settingList)
        recyclerView!!.adapter = settingAdapter
        recyclerView!!.setHasFixedSize(true)
    }

    private fun initData() {
        settingList.add(Settings(
            "android 10",
            "Version 10",
            "API Level 29",
            "This version list description."
        ))
    }
}