package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatisticsActivity : AppCompatActivity() {

    var bills= mutableListOf<Expenses>()
    var getBills= mutableListOf<Expenses>()
    lateinit var switchBtn : SwitchCompat
    lateinit var categoriesBtn: ImageView
    lateinit var removeFragBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        supportActionBar?.hide()

        switchBtn= findViewById(R.id.idSwitch)
        categoriesBtn= findViewById(R.id.statistiks_iv)
        removeFragBtn= findViewById(R.id.remvFrag_iv)

        var db= Firebase.firestore

        bills.add(Expenses("Food",3)) //R.drawable
        bills.add(Expenses("Clothes",3))
        bills.add(Expenses("Films",5))
        bills.add(Expenses("Furniture",10))
        bills.add(Expenses("House Bills",3))
        bills.add(Expenses("Transport",9))

        val recyclerView=findViewById<RecyclerView>(R.id.categ_RV)
        recyclerView.layoutManager= LinearLayoutManager(this)

        val adapter=ExpensesRecyclerAdapter(this,DataManager.catBills)
        recyclerView.adapter= adapter



        switchBtn.setOnClickListener{
            if (switchBtn.isChecked){
                createBudgetDialog()
                switchBtn.isChecked=false

                Log.d("!!!", "${switchBtn.isChecked}")
            }else{
                Log.d("!!!", "${switchBtn.isChecked}")
            }
        }
    }


    fun addCategoriesFragment(view: View){
        val categoriesFragment = CategoryFragment()
        if(!categoriesFragment.isAdded) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, categoriesFragment, "categories_fragment")
            transaction.commit()
            Log.d("!!!", "pressed")
        }else{
            Toast.makeText(this,"Checked", Toast.LENGTH_SHORT).show()
        }

    }
    fun removeFragment(view: View){
        val categoriesFragment= supportFragmentManager.findFragmentByTag("categories_fragment")
        if(categoriesFragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(categoriesFragment)
            transaction.commit()
        }else{
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }

    }
    fun createBudgetDialog(){
        val budget= BudgetDialog()
        budget.show(supportFragmentManager,"!!!" )
    }



}