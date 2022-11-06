package com.example.cashy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*

class DisplayMonths : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    lateinit var dates : MutableList<Receipt>

    lateinit var frameJan : FrameLayout
    lateinit var frameFeb : FrameLayout
    lateinit var frameMar : FrameLayout
    lateinit var frameApr : FrameLayout
    lateinit var frameMay : FrameLayout
    lateinit var frameJun : FrameLayout
    lateinit var frameJul : FrameLayout
    lateinit var frameAug : FrameLayout
    lateinit var frameSep : FrameLayout
    lateinit var frameOct : FrameLayout
    lateinit var frameNov : FrameLayout
    lateinit var frameDec : FrameLayout

    var sumJan = 0
    var sumFeb = 0
    var sumMar = 0
    var sumApr = 0
    var sumMay = 0
    var sumJun = 0
    var sumJul = 0
    var sumAug = 0
    var sumSep = 0
    var sumOct = 0
    var sumNov = 0
    var sumDec = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_months)
        supportActionBar?.hide()

        db = Firebase.firestore
        auth = Firebase.auth

        frameJan = findViewById(R.id.frameJan)
        frameFeb = findViewById(R.id.frameFeb)
        frameMar = findViewById(R.id.frameMar)
        frameApr = findViewById(R.id.frameApr)
        frameMay = findViewById(R.id.frameMay)
        frameJun = findViewById(R.id.frameJun)
        frameJul = findViewById(R.id.frameJul)
        frameAug = findViewById(R.id.frameAug)
        frameSep = findViewById(R.id.frameSep)
        frameOct = findViewById(R.id.frameOct)
        frameNov = findViewById(R.id.frameNov)
        frameDec = findViewById(R.id.frameDec)

        readToJan()
        readToFeb()
        readToMar()
        readToApr()
        readToMay()
        readToJun()
        readToJul()
        readToAug()
        readToSep()
        readToOct()
        readToNov()
        readToDec()

    }
    fun readToJan() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "0")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountJanView = findViewById<TextView>(R.id.amountJan)
                            dates = mutableListOf()
                            dates.add(item)
                            sumJan += item.sum!!
                            amountJanView.text = sumJan.toString()
                            when (sumJan) {
                                in 1000..9999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToFeb() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "1")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountFebView = findViewById<TextView>(R.id.amountFeb)
                            dates = mutableListOf()
                            dates.add(item)
                            sumFeb += item.sum!!
                            amountFebView.text = sumFeb.toString()
                            when (sumFeb) {
                                in 1000..9999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToMar() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "2")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountMarView = findViewById<TextView>(R.id.amountMar)
                            dates = mutableListOf()
                            dates.add(item)
                            sumMar += item.sum!!
                            amountMarView.text = sumMar.toString()
                            when (sumMar) {
                                in 1000..9999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToApr() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "3")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountAprView = findViewById<TextView>(R.id.amountApr)
                            dates = mutableListOf()
                            dates.add(item)
                            sumApr += item.sum!!
                            amountAprView.text = sumApr.toString()
                            when (sumApr) {
                                in 1000..9999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToMay() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "4")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountMayView = findViewById<TextView>(R.id.amountMay)
                            dates = mutableListOf()
                            dates.add(item)
                            sumMay += item.sum!!
                            amountMayView.text = sumMay.toString()
                            when (sumMay) {
                                in 1000..9999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToJun() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "5")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountJunView = findViewById<TextView>(R.id.amountJun)
                            dates = mutableListOf()
                            dates.add(item)
                            sumJun += item.sum!!
                            amountJunView.text = sumJun.toString()
                            when (sumJun) {
                                in 1000..9999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToJul() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "6")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountJulView = findViewById<TextView>(R.id.amountJul)
                            dates = mutableListOf()
                            dates.add(item)
                            sumJul += item.sum!!
                            amountJulView.text = sumJul.toString()
                            when (sumJul) {
                                in 1000..9999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToAug() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "7")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountAugView = findViewById<TextView>(R.id.amountAug)
                            dates = mutableListOf()
                            dates.add(item)
                            sumAug += item.sum!!
                            amountAugView.text = sumAug.toString()
                            when (sumAug) {
                                in 1000..9999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToSep() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "8")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountSepView = findViewById<TextView>(R.id.amountSep)
                            dates = mutableListOf()
                            dates.add(item)
                            sumSep += item.sum!!
                            amountSepView.text = sumSep.toString()
                            when (sumSep) {
                                in 1000..9999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToOct() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "9")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountOctView = findViewById<TextView>(R.id.amountOct)
                            dates = mutableListOf()
                            dates.add(item)
                            sumOct += item.sum!!
                            amountOctView.text = sumOct.toString()
                            when (sumOct) {
                                in 1000..9999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
    fun readToNov() {
        val user = auth.currentUser
        if (user != null) {
           val docRef = db.collection("users").document(user.uid).collection("receipts")
               .whereEqualTo("monthNo", "10") //means november (kotlin starts counting months at 0)
                docRef.addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                val amountNovView = findViewById<TextView>(R.id.amountNov)
                                dates = mutableListOf()
                                dates.add(item)
                                sumNov += item.sum!!
                                amountNovView.text = sumNov.toString()
                                when (sumNov) {
                                    in 1000..9999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                    in 10000..14999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                    in 15000..19999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                    in 20000..24999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                    in 25000..29999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                    in 30000..1000000000 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                                }
                            }
                        }
                    }
                }
        }
    }
    fun readToDec() {
        val user = auth.currentUser
        if (user != null) {
            val docRef = db.collection("users").document(user.uid).collection("receipts")
                .whereEqualTo("monthNo", "11")
            docRef.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val item = document.toObject<Receipt>()
                        if (item != null) {
                            val amountDecView = findViewById<TextView>(R.id.amountDec)
                            dates = mutableListOf()
                            dates.add(item)
                            sumDec += item.sum!!
                            amountDecView.text = sumDec.toString()
                            when (sumDec) {
                                in 1000..9999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                in 10000..14999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                in 15000..19999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                in 20000..24999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                in 25000..29999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                in 30000..1000000000 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                            }
                        }
                    }
                }
            }
        }
    }
}