package com.example.cashy

import com.google.firebase.firestore.DocumentId

class Expense(var sum : Int? = null,
              var category : String? = null,
              var paymentmethod : String? = null,
              var company : String? = null) {}