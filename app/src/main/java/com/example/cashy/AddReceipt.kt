package com.example.cashy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddReceipt : AppCompatActivity() {
    lateinit var addMsg: EditText
    lateinit var addCompany: EditText
    lateinit var addValue: EditText
    lateinit var addCategory: EditText
    lateinit var addPaymentmethod: EditText


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var saveButton: Button
    lateinit var exitButton: FloatingActionButton

    val receipts = mutableListOf<Receipt>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)
        supportActionBar?.hide()

        db = Firebase.firestore
        auth = Firebase.auth

        saveButton = findViewById(R.id.saveReceiptButton)
        saveButton.setOnClickListener {
            if (addValue.text.isEmpty() || addCategory.text.isEmpty() || addPaymentmethod.text.isEmpty() ) {
                Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                saveItem()
            }
        }
        addValue = findViewById(R.id.addValue)
        addMsg = findViewById(R.id.addMsg)
        addCompany = findViewById(R.id.addCompany)
        addCategory = findViewById(R.id.addCategory)
        addPaymentmethod = findViewById(R.id.addPaymentmethod)

        exitButton = findViewById(R.id.exitAddButton)
        exitButton.setOnClickListener{
            exitActivity()
        }
    }

    fun saveItem() {
        addValue = findViewById(R.id.addValue)
        addMsg = findViewById(R.id.addMsg)
        addCompany = findViewById(R.id.addCompany)
        addCategory = findViewById(R.id.addCategory)
        addPaymentmethod = findViewById(R.id.addPaymentmethod)

        val item = Receipt(

            sum = addValue.text.toString().toInt(),
            company = addCompany.text.toString(),
            notis = addMsg.text.toString(),
            category = addCategory.text.toString(),
            paymentmethod = addPaymentmethod.text.toString(),
            timestamp = java.util.Date()
        )
        addValue.setText("")
        addCompany.setText("")
        addMsg.setText("")
        addCategory.setText("")
        addPaymentmethod.setText("")


        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("users").document(user.uid).collection("receipts")
            .add(item)
            .addOnCompleteListener {
                receipts.add(item)
                Toast.makeText(baseContext, "Saved to cloud!",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun exitActivity(){
        val exitTheActivityIntent = Intent(/* packageContext = */ this, /* cls = */ Overview::class.java)
        startActivity(exitTheActivityIntent)

    }
}