package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddReceiptActivity : AppCompatActivity() {
    private lateinit var addMsg: EditText
    private lateinit var addCompany: EditText
    private lateinit var addValue: EditText

    private val M = Calendar.getInstance()
    private val month = M.get(Calendar.MONTH)
    private val Y = Calendar.getInstance()
    private val year = Y.get(Calendar.YEAR)

    lateinit var spinnerCat : Spinner
    lateinit var spinnerPay : Spinner

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
            if (addValue.text.isEmpty() || spinnerCat.selectedItem == "Select Category*" || spinnerPay.selectedItem == "Select payment method*") {
                Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                saveItem()
            }
        }
        addValue = findViewById(R.id.addValue)
        addMsg = findViewById(R.id.addMsg)
        addCompany = findViewById(R.id.addCompany)

        spinnerCat = findViewById(R.id.spinnerCategory)
        spinnerPay = findViewById(R.id.spinnerPaymentmethod)

        exitButton = findViewById(R.id.exitAddButton)
        exitButton.setOnClickListener{
            exitActivity()
        }
    }

    // gets the input data and uses it to create a receipt object that is uploaded to firestore /arvid
    private fun saveItem() {

        addValue = findViewById(R.id.addValue)
        addMsg = findViewById(R.id.addMsg)
        addCompany = findViewById(R.id.addCompany)

        spinnerCat = findViewById(R.id.spinnerCategory)
        spinnerPay = findViewById(R.id.spinnerPaymentmethod)


        if (spinnerCat != null) {
            spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {}
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        if (spinnerPay != null) {
            spinnerPay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {}
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        val item = Receipt(
            sum = addValue.text.toString().toInt(),
            company = addCompany.text.toString(),
            notis = addMsg.text.toString(),
            category = spinnerCat.selectedItem.toString(),
            paymentMethod = spinnerPay.selectedItem.toString(),
            timestamp = Date(),
            monthNo = month.toString(),
            year = year.toString()
        )

        addValue.setText("")
        addCompany.setText("")
        addMsg.setText("")

        val user = auth.currentUser ?: return

        db.collection("users").document(user.uid).collection("receipts")
            .add(item)
            .addOnCompleteListener {
                receipts.add(item)
                Toast.makeText(baseContext, "Saved to cloud!",
                    Toast.LENGTH_SHORT).show()
            }
    }
    private fun exitActivity() {
        finish()
    }
}