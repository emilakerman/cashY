package com.example.cashy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var getStarted_buttonView : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        getStarted_buttonView = findViewById(R.id.getstarted_button)
        getStarted_buttonView.setOnClickListener{
            val login = Intent(this, Login::class.java)
            startActivity(login)
        }
    //joel first commit
    }
}