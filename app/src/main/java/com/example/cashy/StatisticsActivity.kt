package com.example.cashy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashy.fragments.CategoryFragment
import com.example.cashy.fragments.DatePickerFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class StatisticsActivity : AppCompatActivity() {

    private lateinit var addButton : FloatingActionButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var barRecyclerView: RecyclerView
    private lateinit var categoriesBtn: ImageButton //the 2 imgView that manage fragments
    private lateinit var removeFragBtn: ImageButton
    private lateinit var overviewBtn: ImageButton
    private lateinit var calendarBtn: ImageButton
    private lateinit var dailyBudget: TextView

    lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        supportActionBar?.hide()

        categoriesBtn= findViewById(R.id.statistiks_iv)
        removeFragBtn= findViewById(R.id.remvFrag_iv)
        overviewBtn= findViewById(R.id.overview_imgBtn)
        calendarBtn= findViewById(R.id.calendarView)

        db= Firebase.firestore
        auth = Firebase.auth


        //links the RV-Layout with the View      // RV in Activity
        recyclerView=findViewById(R.id.categ_RV)
        recyclerView.layoutManager= LinearLayoutManager(this)
        readFromDatabase()
        //Bar RecyclerView
        barRecyclerView=findViewById(R.id.barRV)
        barRecyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        overviewBtn.setOnClickListener {
            finish()
        }

        addButton = findViewById(R.id.floatingActionButton3)
        addButton.setOnClickListener {
            val intent = Intent(this, AddReceiptActivity::class.java)
            startActivity(intent)
        }

        calendarBtn.setOnClickListener { showDatePickerDialog() }
    }

    fun createBudgetDialog() {
        var budgetAmount=""
        val budget= BudgetDialog()
        budget.show(supportFragmentManager,"budget_dialog")
        dailyBudget.text = budget.popUpBudget
        Log.d("!!!","Budget in main: ${dailyBudget.text}")
    }

    fun withEditText() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.budget_popup, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.budgetET)

        with(builder) {
            setTitle("Daily Budget.")
            setPositiveButton("save") { dialog, which ->
                dailyBudget.text=editText.getText().toString()
                Log.d("!!!","Budget is: $dailyBudget")
            }
            setNegativeButton("cancel") { dialog, which ->

            }
            setView(dialogLayout)
            show()
        }
    }

    // creates a fragment for the categories, and adds it to the container view /arvid
    fun addCategoriesFragment(view: View) {
        val fm=supportFragmentManager.findFragmentByTag("categories_fragment")
        if(fm==null) {
            val categoriesFragment = CategoryFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, categoriesFragment, "categories_fragment")
            transaction.commit()
            Log.d("!!!", "fragment created")
        }
        else {
            Toast.makeText(this,"Checked",Toast.LENGTH_SHORT).show()
        }
    }

    // removes the fragments for categories and the calendar /arvid
    fun removeFragment() {
        val categoriesFragment= supportFragmentManager.findFragmentByTag("categories_fragment")
        val calendarFragment=supportFragmentManager.findFragmentByTag("calendar_fragment")
        if(categoriesFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(categoriesFragment)
            transaction.commit()
        }
        else {
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }

        if(calendarFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(calendarFragment)
            transaction.commit()
        }
        else {
            Toast.makeText(this,"Calendar Fragment not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker= DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager,"Date_Picker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val mo= month+1
        addCalendarFragment(day.toString().padStart(2,'0'),
            mo.toString().padStart(2,'0'),
            year.toString())
    }

    // creates a fragment for the calendar and adds it to the container view /arvid
    private fun addCalendarFragment(day: String, month: String, year: String) {
        val fm = supportFragmentManager.findFragmentByTag("calendar_fragment")
        val calendarFragment = CalendarFragment.newInstance(day, month, year)
        if(fm!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(fm)
            transaction.commit()
        }else{
            Toast.makeText(this,"Calendar Fragment not found", Toast.LENGTH_SHORT).show()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, calendarFragment, "calendar_fragment")
        transaction.commit()
        Log.d("!!!", "calendar fragment created")
        Log.d("!!!","$day.$month.$year")
    }

    //RV adapter according to list needed
    private fun setAdapters(list: List<Receipt>) {
        val adapter=CategoryRecyclerAdapter(this,list)
        recyclerView.adapter= adapter
    }

    private fun progressBarRV(dbList: List<Receipt>) {
        val barAdapter=BarRecyclerAdapter(this,dbList)
        barRecyclerView.adapter= barAdapter
    }

    // reads all of the current user's receipts from firestore, and adds it to the list of receipts 
    // if the category or purchase month of a receipt doesn't exist, it's created and added to their respective lists /arvid
    private fun readFromDatabase() {
        val list= mutableListOf<Receipt>()
        val user= auth.currentUser
        if(user!=null){
            db.collection("users")
                .document(user.uid).collection("receipts")
                .addSnapshotListener{ snapshot, _ ->
                    val existingCategories= mutableListOf<String>()
                    val getMonth= mutableListOf<String>()
                    if(snapshot!=null) {
                        for(document in snapshot.documents){
                            val item= document.toObject<Receipt>()
                            list.add(item!!)

                            if (item.category !in existingCategories) {
                                existingCategories.add(item.category!!)
                            }

                            if (item.monthNo !in getMonth) {
                                getMonth.add(item.monthNo!!)
                            }
                        }
                    }
                    //takes a function that returns a list to set the RV
                    progressBarRV(filterByMonth(getMonth,list))
                    setAdapters(filterByCategory(existingCategories,list))
                }
        }
    }

    //takes 2 lists to create a filtered list for the RV
    private fun filterByMonth(monthList: List<String>, dbList: List<Receipt>): MutableList<Receipt> {
        val countList = mutableListOf<Receipt>()
        val year="2022"
        for (month in monthList) {
            var total = 0
            //Creates a month
            val count = Receipt(category = month, monthNo = month)

            for (item in dbList) {
                //that month of that year
                if (item.monthNo == month && item.year==year) {
                    total += item.sum!!
                }
            }
            count.sum=total
            countList.add(count)
        }

        countList.sortBy { it.category?.toInt() }
        Log.d("!!!","${countList.size}")
        return countList
    }

    // filters the receipt list by category /arvid
    private fun filterByCategory(categories: List<String>, dbList:List<Receipt>): MutableList<Receipt> {
        val countList= mutableListOf<Receipt>()
        for (category in categories) {
            var total=0
            var transactions=0
            val count=Receipt(category=category)
            for (item in dbList) {
                if (item.category==category) {
                    total+=item.sum!!
                    transactions++
                }
            }
            count.sum=total
            count.paymentMethod=""
            count.company="$transactions transactions"
            count.fullDate=""
            countList.add(count)
        }
        return countList
    }
}