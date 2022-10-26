package com.example.cashy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Overview : AppCompatActivity() {

    lateinit var toAddReceiptButton : FloatingActionButton
    lateinit var settings_img : ImageView

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

        //DUMMY DATA - Dessa objekt skall ändras till variablar och tas från en databas
    var expenses = mutableListOf<Expense>(
        Expense(50, "shopping", "debit"),
        Expense(70, "pleasure", "cash"),
        Expense(120, "medical", "debit")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        supportActionBar?.hide()

        //starting up the firestore database and authentication
        val db = Firebase.firestore
        auth = Firebase.auth

        //finding the current user and assigning a variable to the user id
        val user = Firebase.auth.currentUser
        var uid = user?.uid

        //test för att dra ut en lång lista i recyclerview
        for (i in 1..100) {
            expenses.add(Expense(420, "test123", "debit"))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ExpenseRecycleAdapter(this, expenses)

        recyclerView.adapter = adapter


        toAddReceiptButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        toAddReceiptButton.setOnClickListener{
            toAddReceiptButton()
        }
        settings_img = findViewById(R.id.settings_img)
        settings_img.setOnClickListener {
            val settingsLink = Intent(this, SettingsActivity::class.java)
             startActivity(settingsLink)
        }

    }
       fun toAddReceiptButton(){
           val resultButtonIntent = Intent(/* packageContext = */ this, /* cls = */ AddReceipt::class.java)
            startActivity(resultButtonIntent)
    }


}