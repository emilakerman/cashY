package com.example.cashy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

class ListFullScreen : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_full_screen)
        supportActionBar?.hide()

        db = Firebase.firestore
        auth = Firebase.auth

        //finding the current user and assigning a variable to the user id
        val user = Firebase.auth.currentUser
        var uid = user?.uid

        val receipts = mutableListOf<Receipt>()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFullScreen)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FullScreenAdapter(this, receipts)
        recyclerView.adapter = adapter
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
        readFrom()
        val goBack = findViewById<FloatingActionButton>(R.id.backButton)
        goBack.setOnClickListener {
            val intent = Intent(this, Overview::class.java)
            startActivity(intent)
        }
    }
    fun readFrom() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("receipts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    //recyclerview finns både här och i onCreate... crash issues, låt det vara så här for now
                    val receipts = mutableListOf<Receipt>()
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFullScreen)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    val adapter = FullScreenAdapter(this, receipts)
                    recyclerView.adapter = adapter
                    recyclerView.apply {
                        setHasFixedSize(true)
                        addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
                    }
                    for (document in documentSnapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            receipts.add(item)
                        }
                    }
                }
        }
    }
}