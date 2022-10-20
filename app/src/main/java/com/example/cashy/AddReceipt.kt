package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddReceipt : AppCompatActivity() {
    lateinit var addNotification : EditText
    lateinit var addCompany:EditText
    lateinit var addValue : EditText

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var saveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)

        db = Firebase.firestore
        auth = Firebase.auth

        //nameView = findViewById(R.id.nameView)

        saveButton = findViewById<Button>(R.id.addValue)
        saveButton.setOnClickListener {
            saveItem()

        }

        // read receipts - kommenterade detta för visste inte om vi ska använda recycler view
        /*val user = auth.currentUser
        if(user != null) {
            db.collection("users").document(user.uid)
                .collection("items")
                .addSnapshotListener { snapshot , e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val receipt = document.toObject<Receipt>()
                            Log.d("!!!", "item: ${receipt}")
                        }
                    }


                }


        }*/


    }

    fun saveItem() {
        val item = Receipt(sum = addValue.text.toString())
        addValue.setText("")
        val ItemCompany = Receipt(company = addCompany.text.toString())
        addCompany.setText("")
        val itemText = Receipt(notis = addNotification.text.toString())
        addNotification.setText("")


        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("users").document(user.uid).collection("receipt").add(item) //Hur lägger man i fler
            .addOnCompleteListener {
                Log.d("!!!", "add item")
            }
                //Här kan vi använda en "Alert" "att det är sparad"

    }
}


















