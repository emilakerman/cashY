package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StatisticsActivity : AppCompatActivity() {

    var bills= mutableListOf<Expenses>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        bills.add(Expenses("Food",3)) //R.drawable
        bills.add(Expenses("Clothes",3))
        bills.add(Expenses("Films",5))
        bills.add(Expenses("Furniture",10))
        bills.add(Expenses("House Bills",3))
        bills.add(Expenses("Transport",9))

        var recyclerView=findViewById<RecyclerView>(R.id.categ_RV)
        recyclerView.layoutManager= LinearLayoutManager(this)

        val adapter=CategoryRecyclerAdapter(this,bills)

        recyclerView.adapter= adapter


    }



}