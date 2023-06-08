package com.example.cashy

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class DisplayMonthsActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var dates : MutableList<Receipt>

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

    lateinit var backButton : FloatingActionButton

    //fragment imageButtons
    lateinit var previousYear : ImageButton
    lateinit var nextYear : ImageButton

    lateinit var staticYear : Button



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

    var monthSums = mutableListOf(sumJan, sumFeb, sumMar, sumApr, sumMay, sumJun, sumJul, sumAug, sumSep, sumOct, sumNov, sumDec)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_months)
        supportActionBar?.hide()

        db = Firebase.firestore
        auth = Firebase.auth

        //fragment imageButtons
        previousYear = findViewById(R.id.previousYear)
        nextYear = findViewById(R.id.nextYear)

        backButton = findViewById(R.id.backToOverview)
        backButton.setOnClickListener {
            finish()
        }
        
        staticYear = findViewById(R.id.staticYear)
        staticYear.text = "2022"

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

        // this should really be handled by a loop /arvid
        // this should work better (i hope)
        val monthFrames = mutableListOf(frameJan, frameFeb, frameMar, frameApr, frameMay, frameJun, frameJul, frameAug, frameSep, frameOct, frameNov, frameDec)
        val amountViews = mutableListOf<TextView>(findViewById(R.id.amountJan), findViewById(R.id.amountFeb), findViewById(R.id.amountMar), findViewById(R.id.amountApr), findViewById(R.id.amountMay), findViewById(R.id.amountJun), findViewById(R.id.amountJul), findViewById(R.id.amountAug), findViewById(R.id.amountSep), findViewById(R.id.amountOct), findViewById(R.id.amountNov), findViewById(R.id.amountDec))
        readToMonths(monthFrames, amountViews)
    }

    fun nextYear(view : View) { // this function is attached to the element in the XML file's "onclick", shows the data for the next year /arvid
        val fm = supportFragmentManager.findFragmentByTag("year_fragment")
        if (fm == null) {
            val yearFragment = YearFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, yearFragment, "year_fragment")
            transaction.commit()
        }
        else {
            Toast.makeText(this,"No more years have been added", Toast.LENGTH_SHORT).show()
        }
    }

    fun previousYear(view : View) { // this function is attached to the element in the XML file's "onclick", shows the data for the previous year /arvid
        val yearFragment = supportFragmentManager.findFragmentByTag("year_fragment")
        if (yearFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(yearFragment)
            transaction.commit()
        } 
        else {
            Toast.makeText(this,"2022 is the first year of the app", Toast.LENGTH_SHORT).show()
        }
    }

    //what is this repetition? trying to put together a more generic function /arvid
    //i can't really test this function properly due to the firestore security rules, but i think this will work
    // this function fetches all of the users receipts from firestore, sorted by month, and adds the data to each months Framelayout
    private fun readToMonths(monthFrames: MutableList<FrameLayout>, amountViews : MutableList<TextView>) {
        val user = auth.currentUser
        if (user != null) {
            amountViews.forEachIndexed { index, amountView ->
                val docRef = db.collection("users").document(user.uid).collection("receipts")
                    .whereEqualTo("monthNo", "${index + 1}")
                    .whereEqualTo("year", "2022")
                docRef.addSnapshotListener { snapshot, _ ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Receipt>()
                            if (item != null) {
                                dates = mutableListOf()
                                dates.add(item)
                                monthSums[index] += item.sum!!
                                amountView.text = monthSums[index].toString()
                                val monthFrame = monthFrames[index]
                                when (monthSums[index]) {
                                    in 1000..9999 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
                                    in 10000..14999 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
                                    in 15000..19999 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
                                    in 20000..24999 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
                                    in 25000..29999 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
                                    in 30000..1000000000 -> monthFrame.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
                                }

                                if (monthSums[index] >= 100000) {
                                    amountView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}