package com.example.cashy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = Firebase.auth

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val i = Intent(this, OverviewActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        val registerText: TextView = findViewById(R.id.textView_register)
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.button_login)
        loginButton.setOnClickListener {
            performLogin()
        }
    }

    // if the user has filled in both email and password, tries to log in with firebase auth /arvid
    private fun performLogin() {
        val email : EditText = findViewById(R.id.editText_email_login)
        val password : EditText = findViewById(R.id.editText_password_login)

        if (email.text.isEmpty() && password.text.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, OverviewActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Success.", Toast.LENGTH_SHORT).show()
                } 
                else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Authentication failed. ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}