package com.example.cashy


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expandablerecycleviewkotlin.SettingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class SettingsActivity : AppCompatActivity() {

    private val settingList = ArrayList<Settings>()
    lateinit var logoutButtonView: Button
    lateinit var backButton : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        backButton = findViewById(R.id.floatingActionButton)
        backButton.setOnClickListener {
            finish()
        }

        logoutButtonView = findViewById(R.id.buttonLogOut)
        logoutButtonView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

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
                "CashY Expense Tracker",
                "",
                "By using CashY you will keep track of your economy. It enables its users to track and manage personal finance budget and analyze their expenses."
            )
        )
        settingList.add(
            Settings(
                "Personal data",
                "Our use of your personal data",
                "",
                "Primarily, we use your personal data to enable features in CashY such as: \n" +
                        "* Data synchronisation between multiple devices. \n" +
                        "* Backup of your data in case you need to install CashY from scratch on a new device. \n" +
                        "* Data synchronization between members of a budget \n" +
                        "CashY will not share your personal data with third parties without your permission, except in the limited circumstances provided below. Personal data collected from you may be shared with our affiliates, agents and business partners. We may disclose your personal data in order to comply with a legal or regulatory obligation, if we reasonably believe that this is required by law, regulation or other legislation, or in order to protect and defend CashY, our business partners or users’ rights and interests.  \n" +
                        "\n" +
                        " "
            )
        )
        settingList.add(
            Settings(
                "Terms & Conditions",
                "Read our terms & conditions below",
                "",
                "CashY is a mobile phone application for various platforms, developed and offered to you by CashY AB. It enables its users to track and manage personal finance budget and analyze their expenses. Your use of the CashY mobile phone application and all the related services provided through it by Provider (collectively referred as \"CashY\") is subject to the following terms (“Terms”), which upon your acceptance form a legally binding agreement between you and the Provider. For the avoidance of doubt, such agreement is concluded solely between you and the Provider and there are no other parties to it. CashY currently available for download free of charge. Provider does not guarantee that CashY or any of its features will always be free and reserves the right, at its sole discretion, to change the pricing at any time.\u2028 Acceptance These Terms regulate the legal relationship between you and the Provider. You may not use CashY if you do not accept them. By downloading, installing, using or otherwise accessing CashY, you are expressing your acceptance and willingness to be bound by the Terms. You may not accept these Terms unless you are at least 13 years of age and you have sufficient legal capacity to enter into a contract. If you are 13 or older but less than 18 years of age, you must have your parent or legal guardian’s permission to accept the Terms and use CashY. You are solely responsible for all activities on your account and the content that is uploaded and/or created under your CashY account. Your membership, including your email and password, with CashY is personal and may not be transferred or used by someone else. You are responsible for storing your login details in a safe manner. Provider is not in any way responsible for any loss or damage caused by unauthorized access to your account or use of your login details.  "
            )
        )
        settingList.add(
            Settings(
                "Legal",
                "Governing law and dispute resolution",
                "",
                "These Terms and the use of the Services are governed by the laws of Sweden, except for its conflicts of laws principles. All claims arising out of or relating to these Terms or the Service shall be resolved by the public Swedish courts, whereby the District Court of Stockholm shall be the court of first instance."
            )
        )
    }
}