package com.example.cashy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*


class Overview : AppCompatActivity() {

    lateinit var toAddReceiptButton : FloatingActionButton
    lateinit var timeShow : ImageView
    lateinit var statisticsLink : Button
    lateinit var settingsLink : Button
    lateinit var monthLink : Button

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var totalSum : Int = 0
    var cashSum : Int = 0
    var cardSum : Int = 0

    lateinit var receipts : MutableList<Receipt>
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : ExpenseRecycleAdapter

    val c = Calendar.getInstance()
    var currentMonth = (c.get(Calendar.MONTH) + 1).toString()
    var currentYear = c.get(Calendar.YEAR).toString()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        supportActionBar?.hide()

        //starting up the firestore database and authentication
        db = Firebase.firestore
        auth = Firebase.auth

        //finding the current user and assigning a variable to the user id
        val user = Firebase.auth.currentUser
        var uid = user?.uid

        receipts = mutableListOf()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseRecycleAdapter(receipts)
        recyclerView.adapter = adapter

        timeShow = findViewById(R.id.timeShow)
        timeShow.setOnClickListener {
            showTime()
        }
        statisticsLink = findViewById(R.id.statisticsLink)
        statisticsLink.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
        monthLink = findViewById(R.id.monthLink)
        monthLink.setOnClickListener {
            val intent = Intent(this, DisplayMonths::class.java)
            startActivity(intent)
        }

        //reads data and populates the recyclerview
        readFrom()
        //reads total SUM data and adds to total sum up top
        readTotalSum()
        //reads total of CASH SUM and adds to correct place
        readToPaymentmethodCash()
        //reads total of CARD SUM and adds to correct place
        readToPaymentmethodCard()


        toAddReceiptButton = findViewById(R.id.floatingActionButton2)
        toAddReceiptButton.setOnClickListener {
            toAddReceiptButton()
        }
        settingsLink = findViewById(R.id.settingsLink)
        settingsLink.setOnClickListener {
            val settingsLink = Intent(this, SettingsActivity::class.java)
            startActivity(settingsLink)
        }
    }
    fun showTime() {
        val intent1 = Intent(this, ListFullScreen::class.java)
        startActivity(intent1)
    }
    @SuppressLint("SuspiciousIndentation")
    fun readToPaymentmethodCard(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Card")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)
                docRef.addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                val cardAmount = findViewById<TextView>(R.id.cardAmount)
                                receipts = mutableListOf()
                                receipts.add(item)
                                cardSum += item.sum!!
                                cardAmount.text = "${cardSum} kr"
                            }
                        }
                    }
                }
        }
    }
    fun readToPaymentmethodCash(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Cash")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)
                docRef.addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                val cashAmount = findViewById<TextView>(R.id.cashAmount)
                                receipts = mutableListOf()
                                receipts.add(item)
                                cashSum += item.sum!!
                                cashAmount.text = "${cashSum} kr"
                            }
                        }
                    }
                }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun readFrom(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                //.whereEqualTo("monthNo", currentMonth)
                //.whereEqualTo("year", currentYear)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                docRef.addSnapshotListener { snapshot, e ->
                        receipts = mutableListOf()
                        recyclerView = findViewById(R.id.recyclerView)
                        adapter = ExpenseRecycleAdapter(receipts)
                        recyclerView.adapter = adapter
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                receipts.add(item)
                            }
                        }
                    }
                }
        }
    }
       fun toAddReceiptButton() {
           val resultButtonIntent =
               Intent(this, AddReceipt::class.java)
           startActivity(resultButtonIntent)
       }
    //reads total sum of all fields correctly and populates the top total sum textview
    private fun readTotalSum(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)
                docRef.addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                val totalspent_txt = findViewById<TextView>(R.id.totalSpent_txt)
                                receipts = mutableListOf()
                                receipts.add(item)
                                totalSum += item.sum!!
                                totalspent_txt.text = "${totalSum} kr"
                            }
                        }
                    }
                }
        }
    }
}