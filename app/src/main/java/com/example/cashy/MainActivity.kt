package com.example.cashy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var getStarted_buttonView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()




            getStarted_buttonView = findViewById(R.id.getstarted_button)
            getStarted_buttonView.setOnClickListener {
                val login = Intent(this, Login::class.java)
                startActivity(login)
            }
        }
    }
