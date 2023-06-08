package com.example.cashy

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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


class OverviewActivity : AppCompatActivity() {

    private lateinit var toAddReceiptButton : FloatingActionButton
    private lateinit var timeShow : ImageView
    private lateinit var statisticsLink : Button
    private lateinit var settingsLink : Button
    private lateinit var monthLink : Button

    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var totalSum : Int = 0
    private var cashSum : Int = 0
    private var cardSum : Int = 0

    lateinit var receipts : MutableList<Receipt>
    private lateinit var recyclerView : RecyclerView
    lateinit var adapter : ExpenseRecycleAdapter

    val c = Calendar.getInstance()
    private var currentMonth = (c.get(Calendar.MONTH) + 1).toString()
    private var currentYear = c.get(Calendar.YEAR).toString()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        supportActionBar?.hide()

        //starting up the Firestore database and authentication
        db = Firebase.firestore
        auth = Firebase.auth

        //finding the current user and assigning a variable to the user id //This is not used? The Firestore functions get the logged in user from the lateinit auth variable instead, removing it /arvid


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
            val intent = Intent(this, DisplayMonthsActivity::class.java)
            startActivity(intent)
        }

        //reads data and populates the recyclerview
        readFrom()
        //reads total SUM data and adds to total sum up top
        readTotalSum()
        //reads total of CASH SUM and adds to correct place
        readToPaymentMethodCash()
        //reads total of CARD SUM and adds to correct place
        readToPaymentMethodCard()


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

    private fun showTime() {
        val intent = Intent(this, ListFullScreenActivity::class.java)
        startActivity(intent)
    }

    // reads the user's receipts from firebase from the current month where the purchase has been made with card
    // then it adds the data to the lateinit list of receipts and shows how much money was spent on each /arvid
    private fun readToPaymentMethodCard(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Card")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)

            docRef.addSnapshotListener { snapshot, _ ->
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

    // reads the user's receipts from firebase from the current month where the purchase has been made with cash
    // then it adds the data to the lateinit list of receipts and shows how much money was spent on each /arvid    
    private fun readToPaymentMethodCash(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("paymentmethod", "Cash")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)

            docRef.addSnapshotListener { snapshot, _ ->
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

    // gets all of the user's receipts from firestore
    // then it adds the data to the lateinit list of receipts and shows how much money was spent on each /arvid
    private fun readFrom(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                //.whereEqualTo("monthNo", currentMonth)
                //.whereEqualTo("year", currentYear)
                .orderBy("timestamp", Query.Direction.DESCENDING)

            docRef.addSnapshotListener { snapshot, _ ->
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

    private fun toAddReceiptButton() {
        val resultButtonIntent =
            Intent(this, AddReceiptActivity::class.java)
        startActivity(resultButtonIntent)
    }

    //reads total sum of all fields correctly and populates the top total sum textview
    private fun readTotalSum(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", currentMonth)
                .whereEqualTo("year", currentYear)
            docRef.addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val totalSpentTxt = findViewById<TextView>(R.id.totalSpent_txt)
                            receipts = mutableListOf()
                            receipts.add(item)
                            totalSum += item.sum!!
                            // this should done with resource strings with placeholders instead, like what the warning suggests /arvid
                            totalSpentTxt.text = "${totalSum} kr"
                        }
                    }
                }
            }
        }
    }
}