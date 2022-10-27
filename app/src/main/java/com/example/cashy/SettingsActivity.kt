package com.example.cashy


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expandablerecycleviewkotlin.SettingAdapter


class SettingsActivity : AppCompatActivity() {

    private val settingList = ArrayList<Settings>()
    lateinit var logoutButtonView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        logoutButtonView = findViewById(R.id.buttonLogOut)
//        logoutButtonView.layoutManager = LinearLayoutManager(this)
//        logoutButtonView.setOnClickListener {
//            val logOutButton = Intent(this, SettingsActivity::class.java)
//            startActivity(logOutButton)

        initData()
        setRecyclerView()

    }


    private fun setRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this@SettingsActivity)

        val settingAdapter = SettingAdapter(this, settingList)
        recyclerView.adapter = settingAdapter
        recyclerView.setHasFixedSize(true)
    }


    private fun initData() {
        settingList.add(
            Settings(
                "About",
                "Subheading Text",
                "Section text",
                "Description text should be entered here."
            )
        )
        settingList.add(
            Settings(
                "Privacy Policy",
                "Subheading Text",
                "Section text",
                "Description text should be entered here."
            )
        )
        settingList.add(
            Settings(
                "Terms and Conditions",
                "Subheading Text",
                "Section text",
                "Description text should be entered here."
            )
        )
        settingList.add(
            Settings(
                "Legal",
                "Subheading Text",
                "Section text",
                "Description text should be entered here."
            )
        )
        settingList.add(
            Settings(
                "GDPR",
                "Subheading Text",
                "Section text",
                "Description text should be entered here."
            )
        )
    }
}