package com.example.cashy

import android.annotation.SuppressLint
import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class DisplayMonths : AppCompatActivity() {

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

    lateinit var amountJanView : TextView
    var amountJan = 0
    lateinit var amountFebView : TextView
    var amountFeb = 0
    lateinit var amountMarView : TextView
    var amountMar = 0
    lateinit var amountAprView : TextView
    var amountApr = 0
    lateinit var amountMayView : TextView
    var amountMay = 0
    lateinit var amountJunView : TextView
    var amountJun = 0
    lateinit var amountJulView : TextView
    var amountJul = 0
    lateinit var amountAugView : TextView
    var amountAug = 0
    lateinit var amountSepView : TextView
    var amountSep = 0
    lateinit var amountOctView : TextView
    var amountOct = 0
    lateinit var amountNovView : TextView
    var amountNov = 0
    lateinit var amountDecView : TextView
    var amountDec = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_months)
        supportActionBar?.hide()

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
        amountJanView = findViewById(R.id.amountJan)
        amountFebView = findViewById(R.id.amountFeb)
        amountMarView = findViewById(R.id.amountMar)
        amountAprView = findViewById(R.id.amountApr)
        amountMayView = findViewById(R.id.amountMay)
        amountJunView = findViewById(R.id.amountJun)
        amountJulView = findViewById(R.id.amountJul)
        amountAugView = findViewById(R.id.amountAug)
        amountSepView = findViewById(R.id.amountSep)
        amountOctView = findViewById(R.id.amountOct)
        amountNovView = findViewById(R.id.amountNov)
        amountDecView = findViewById(R.id.amountDec)
        amountJan = amountJanView.text.toString().toInt()
        amountFeb = amountFebView.text.toString().toInt()
        amountMar = amountMarView.text.toString().toInt()
        amountApr = amountAprView.text.toString().toInt()
        amountMay = amountMayView.text.toString().toInt()
        amountJun = amountJunView.text.toString().toInt()
        amountJul = amountJulView.text.toString().toInt()
        amountAug = amountAugView.text.toString().toInt()
        amountSep = amountSepView.text.toString().toInt()
        amountOct = amountOctView.text.toString().toInt()
        amountNov = amountNovView.text.toString().toInt()
        amountDec = amountDecView.text.toString().toInt()

        //ändra intervallvärden till variablar. sedan gör så användaren kan välja variablarnas värde?
        when (amountJan) {
            in 1000..9999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameJan.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountFeb) {
            in 1000..9999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameFeb.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountMar) {
            in 1000..9999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameMar.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountApr) {
            in 1000..9999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameApr.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountMay) {
            in 1000..9999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameMay.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountJun) {
            in 1000..9999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameJun.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountJul) {
            in 1000..9999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameJul.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountAug) {
            in 1000..9999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameAug.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountSep) {
            in 1000..9999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameSep.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountOct) {
            in 1000..9999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameOct.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountNov) {
            in 1000..9999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameNov.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
        when (amountDec) {
            in 1000..9999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape2)
            in 10000..14999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape3)
            in 15000..19999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape4)
            in 20000..24999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape5)
            in 25000..29999 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape6)
            in 30000..1000000000 -> frameDec.background = ContextCompat.getDrawable(this, R.drawable.rounded_shape7_final)
        }
    }
}