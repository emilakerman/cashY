package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Overview : AppCompatActivity() {

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
    }
}