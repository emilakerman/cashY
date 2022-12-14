package com.example.cashy

import android.content.DialogInterface
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class StatisticsActivity : AppCompatActivity() {

    lateinit var addButton : FloatingActionButton

    lateinit var recyclerView: RecyclerView
    lateinit var barRecyclerView: RecyclerView
    //lateinit var switchBtn : SwitchCompat
    lateinit var categoriesBtn: ImageButton //the 2 imgView that manage fragments
    lateinit var removeFragBtn: ImageButton
    lateinit var overviewBtn: ImageButton
    lateinit var calendarBtn: ImageButton
    lateinit var dailyBudget:TextView

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        supportActionBar?.hide()

        //switchBtn= findViewById(R.id.idSwitch)  //the 2 imgView that manage fragments
        categoriesBtn= findViewById(R.id.statistiks_iv)
        removeFragBtn= findViewById(R.id.remvFrag_iv)
        overviewBtn= findViewById(R.id.overview_imgBtn)
        calendarBtn= findViewById(R.id.calendarView)
        //dailyBudget=findViewById(R.id.dailyBeditTxt)

        db= Firebase.firestore
        auth = Firebase.auth

        //addDataTillUser()

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
            val intent = Intent(this, AddReceipt::class.java)
            startActivity(intent)
        }


        /*    //Switch btn for Budget popup window
        switchBtn.setOnClickListener{
            if (switchBtn.isChecked){
                //createBudgetDialog()
                withEditText()
                switchBtn.isChecked=false
                Log.d("!!!", "${switchBtn.isChecked}")
            }else{
                Log.d("!!!", "${switchBtn.isChecked}")
            }
        }*/
        calendarBtn.setOnClickListener { showDatePickerDialog() }

    }
    fun createBudgetDialog(){
        var budgetAmmount=""
        val budget= BudgetDialog()
        budget.show(supportFragmentManager,"budget_dialog" )
        dailyBudget.setText(budget.popUpBudget)
        Log.d("!!!","Budget in main: ${dailyBudget.text}")
    }
    fun withEditText() {
        //dailyBudget.setText("")
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.budget_popup, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.budgetET)
        with(builder){
            setTitle("Daily Budget.")
            setPositiveButton("save"){dialog, which ->
                dailyBudget.text=editText.getText().toString()
                Log.d("!!!","Budget is: $dailyBudget")
            }
            setNegativeButton("cancel"){dialog, which ->

            }
            setView(dialogLayout)
            show()
        }
        /*builder.setTitle("Daily Budget.")
        builder.setView(dialogLayout)
        builder.setPositiveButton("save") { dialogInterface, i ->
            dailyBudget.setText(editText.text.toString()+ " Kr")
            Log.d("!!!","Budget is: $dailyBudget")
            Toast.makeText(applicationContext, "Budget is " + editText.text.toString(),
                Toast.LENGTH_SHORT).show()
        }
        builder.show()*/

    }

    fun addCategoriesFragment(view: View){
        val fm=supportFragmentManager.findFragmentByTag("categories_fragment")
        if(fm==null) {
            val categoriesFragment = CategoryFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, categoriesFragment, "categories_fragment")
            transaction.commit()
            Log.d("!!!", "fragment created")
        }else{
            Toast.makeText(this,"Checked",Toast.LENGTH_SHORT).show()
        }

    }
    fun removeFragment(view: View){
        val categoriesFragment= supportFragmentManager.findFragmentByTag("categories_fragment")
        val calendarFragment=supportFragmentManager.findFragmentByTag("calendar_fragment")
        if(categoriesFragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(categoriesFragment)
            transaction.commit()
        }else{
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }
        if(calendarFragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(calendarFragment)
            transaction.commit()
            //dateBox.setText("")
        }else{
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showDatePickerDialog() {
        val datePicker= DatePickerFragment({day,month, year -> onDateSelected(day,month, year)})
        datePicker.show(supportFragmentManager,"Date_Picker")
    }
    private fun onDateSelected(day: Int, month: Int, year:Int) {
        var mo= month+1
        //dateBox.setText("You selected day: $day.$mo.$year")
        addCalendarFragment(day.toString().padStart(2,'0'),
            mo.toString().padStart(2,'0'),
            year.toString())
    }
    fun addCalendarFragment(day:String, month:String, year: String){
        val fm = supportFragmentManager.findFragmentByTag("calendar_fragment")
        val calendarFragment = CalendarFragment.newInstance(day, month, year)
        if(fm!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(fm)
            transaction.commit()
        }else{
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, calendarFragment, "calendar_fragment")
        transaction.commit()
        Log.d("!!!", "calendar fragment created")
        Log.d("!!!","$day.$month.$year")
    }
    //RV adapter acording to list needed
    fun setAdapters(list: List<Receipt>){
        val adapter=CategoryRecyclerAdapter(this,list)
        recyclerView.adapter= adapter
    }
    fun progressBarRV(dbList:List<Receipt>){
        val barAdapter=BarRecyclerAdapter(this,dbList)
        barRecyclerView.adapter= barAdapter
    }
    fun readFromDatabase(){
        val list= mutableListOf<Receipt>()
        val user= auth.currentUser
        if(user!=null){
            db.collection("users")
                .document(user.uid).collection("receipts")
                .addSnapshotListener{ snapshot, e->

                    var existingCategories= mutableListOf<String>()
                    var getMonth= mutableListOf<String>()
                    if(snapshot!=null){
                        for(document in snapshot.documents){
                            val item= document.toObject<Receipt>()
                            list.add(item!!)

                            if (item.category !in existingCategories){
                                existingCategories.add(item.category!!)
                            }
                            if (item.monthNo !in getMonth){
                                getMonth.add(item.monthNo!!)
                            }
                        }
                    }       //takes a function that returns a list to set the RV
                    progressBarRV(filterByMonth(getMonth,list))
                    setAdapters(filterByCategory(existingCategories,list))
                }
        }
    }
    //takes 2 lists to create a filtered list for the RV
    fun filterByMonth(monthList:List<String>, dbList:List<Receipt>): MutableList<Receipt>{
        var countList = mutableListOf<Receipt>()
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
    fun filterByCategory(categories:List<String>, dbList:List<Receipt>):MutableList<Receipt>{
        var countList= mutableListOf<Receipt>()
        for (category in categories){
            var total=0
            var transactions=0
            val count=Receipt(category=category)
            for (item in dbList){
                if (item.category==category){
                    total+=item.sum!!
                    transactions++
                }
            }
            count.sum=total
            count.paymentmethod=""
            count.company="$transactions transactions"
            count.fullDate=""
            countList.add(count)
        }
        return countList
    }
    //just creates data i can use for testing
    fun addDataTillUser(){
        val check1 = Receipt(sum=199, company="Hem", category = "Housing", paymentmethod = "Card")
        val check2 = Receipt(sum=1100, company="Hem", category = "Housing", paymentmethod = "Card")
        val check3 = Receipt(sum=630, company="Hem", category = "Housing", paymentmethod = "Cash")

        val check4 = Receipt(sum=980, company="Apple", category = "Electronics", paymentmethod = "Card")

        val check5 = Receipt(sum=200, company="Media Markt", category = "Electronics", paymentmethod = "Cash")
        val check6 = Receipt(sum=780, company="Microsoft", category = "Electronics", paymentmethod = "Cash")
        val check7 = Receipt(sum=320, company="Google", category = "Electronics", paymentmethod = "Card")

        val check8 = Receipt(sum=560, company="SATS", category = "Sports", paymentmethod = "Card")
        val check9 = Receipt(sum=245, company="24Fitness", category = "Sports", paymentmethod = "Card")
        val check10 = Receipt(sum=490, company="Nike", category = "Sports", paymentmethod = "Card")
        val check11 = Receipt(sum=674, company="Under Armour", category = "Sports", paymentmethod = "Card")
        val check12 = Receipt(sum=2000, company="SAS", category = "Travel", paymentmethod = "Card")

        val check13 = Receipt(sum=6500, company="Iberia", category = "Travel", paymentmethod = "Card")

        val check14 = Receipt(sum=678, company="AIR-tifacts", category = "Travel", paymentmethod = "Cash")
        val check15 = Receipt(sum=5320, company="Vueling", category = "Travel", paymentmethod = "Cash")
        val check16 = Receipt(sum=76, company="Starbucks", category = "Fika", paymentmethod = "Card")
        val check17 = Receipt(sum=98, company="Espresso House", category = "Fika", paymentmethod = "Cash")

        val check18 = Receipt(sum=350, company="Bars", category = "Fika", paymentmethod = "Card")
        val check19 = Receipt(sum=250, company="Starbucks", category = "Fika", paymentmethod = "Card")
        val check20 = Receipt(sum=125, company="Espresso House", category = "Fika", paymentmethod = "Cash")

        val check21 = Receipt(sum=768, company="Nintendo", category = "Entertainment", paymentmethod = "Card")
        val check22 = Receipt(sum=365, company="Fnac", category = "Entertainment", paymentmethod = "Cash")
        val check23 = Receipt(sum=1650, company="PlayStation", category = "Entertainment", paymentmethod = "Cash")
        /*val user= auth.currentUser
        if (user==null){
            return
        }*/
        val user= auth.currentUser ?: return //it declares user and at the same time if it's null return back, saving the steps before
        //add one by one
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check1).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check2).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check3).addOnCompleteListener {
                Log.d("!!!", "Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check4).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check5).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check6).addOnCompleteListener {
                Log.d("!!!", "Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check7).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check8).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check9).addOnCompleteListener {
                Log.d("!!!", "Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check10).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check11).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check12).addOnCompleteListener {
                Log.d("!!!", "Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check13).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check14).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check15).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check16).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check17).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check18).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check19).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check20).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check21).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check22).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check23).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }

    }

}