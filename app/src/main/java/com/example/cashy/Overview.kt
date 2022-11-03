package com.example.cashy

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import android.widget.SearchView


class Overview : AppCompatActivity() {

    lateinit var toAddReceiptButton : FloatingActionButton
    lateinit var settings_img : ImageView
    lateinit var timeShow : ImageView
    lateinit var statisticsLink : ImageView

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var totalSum : Int = 0
    var cashSum : Int = 0
    var cardSum : Int = 0

    lateinit var receipts : MutableList<Receipt>
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : ExpenseRecycleAdapter


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
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
        timeShow = findViewById(R.id.timeShow)
        timeShow.setOnClickListener {
            showTime()
        }
        statisticsLink = findViewById(R.id.statisticsLink)
        statisticsLink.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        //reads data and populates the recyclerview (also enables the recyclerview)
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
        settings_img = findViewById(R.id.settings_img)
        settings_img.setOnClickListener {
            val settingsLink = Intent(this, SettingsActivity::class.java)
            startActivity(settingsLink)
        }
    }
    fun showTime() {
        val intent1 = Intent(this, ListFullScreen::class.java)
        startActivity(intent1)
    }
    fun readToPaymentmethodCard() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Card")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot.documents) {
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
    fun readToPaymentmethodCash() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Cash")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot.documents) {
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
    fun readFrom() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                    .addOnSuccessListener { documentSnapshot ->
                        receipts = mutableListOf()
                        recyclerView = findViewById(R.id.recyclerView)
                        adapter = ExpenseRecycleAdapter(receipts)
                        recyclerView.adapter = adapter
                    for (document in documentSnapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            receipts.add(item)
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
    private fun readTotalSum() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot.documents) {
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
                .addOnFailureListener { exception ->
                    Log.w("!!!", "Error getting documents: ", exception)
                }
        }
    }
}