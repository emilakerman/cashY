package com.example.cashy

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Overview : AppCompatActivity() {

    lateinit var toAddReceiptButton : FloatingActionButton
    lateinit var settings_img : ImageView

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var totalSum : Int = 0
    var cashSum : Int = 0
    var cardSum : Int = 0


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

        //reads data and populates the recyclerview (also enables the recyclerview)
        readFrom()
        //reads total SUM data and adds to total sum up top
        readTotalSum()
        //reads total of CASH SUM and adds to correct place
        readToPaymentmethodCash()
        //reads total of CARD SUM and adds to correct place
        readToPaymentmethodCard()


        //temporary logout button
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        toAddReceiptButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        toAddReceiptButton.setOnClickListener{
            toAddReceiptButton()
        }
        settings_img = findViewById(R.id.settings_img)
        settings_img.setOnClickListener {
            val settingsLink = Intent(this, SettingsActivity::class.java)
            startActivity(settingsLink)
        }
        /////popup menu test
        val card_img = findViewById<ImageView>(R.id.card_img)
        card_img.setOnClickListener {
            val popup = PopupMenu(this, card_img)
            popup.inflate((R.menu.popup))
            popup.show()
        }
    }
    fun readToPaymentmethodCard() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "card")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot.documents) {
                        val item = document.toObject<Expense>()
                        if (item != null) {
                            val cardAmount = findViewById<TextView>(R.id.cardAmount)
                            val expenses = mutableListOf<Expense>()
                            expenses.add(item)
                            cardSum += item.sum!!
                            cardAmount.text = cardSum.toString()
                        }
                    }
                }
        }
    }
    fun readToPaymentmethodCash() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "cash")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    for (document in documentSnapshot.documents) {
                        val item = document.toObject<Expense>()
                        if (item != null) {
                            val cashAmount = findViewById<TextView>(R.id.cashAmount)
                            val expenses = mutableListOf<Expense>()
                            expenses.add(item)
                            cashSum += item.sum!!
                            cashAmount.text = cashSum.toString()
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
                        val expenses = mutableListOf<Expense>()
                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                            recyclerView.layoutManager = LinearLayoutManager(this)
                            val adapter = ExpenseRecycleAdapter(this, expenses)
                            recyclerView.adapter = adapter
                    for (document in documentSnapshot.documents) {
                        val item = document.toObject<Expense>()
                        if (item != null) {
                            expenses.add(item)
                        }
                    }
                }
        }
    }
       fun toAddReceiptButton() {
           val resultButtonIntent =
               Intent(/* packageContext = */ this, /* cls = */ AddReceipt::class.java)
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
                        val item = document.toObject<Expense>()
                        if (item != null) {
                            val totalspent_txt = findViewById<TextView>(R.id.totalSpent_txt)
                            val expenses = mutableListOf<Expense>()
                            expenses.add(item)
                            totalSum += item.sum!!
                            totalspent_txt.text = totalSum.toString()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("!!!", "Error getting documents: ", exception)
                }
        }

    }
}