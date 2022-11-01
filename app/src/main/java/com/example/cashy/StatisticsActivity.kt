package com.example.cashy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class StatisticsActivity : AppCompatActivity() {

    //var bills= mutableListOf<Expenses>()
    //var getBills= mutableListOf<Receipt>()
    var receiptBills= mutableListOf<Receipt>()

    lateinit var recyclerView: RecyclerView
    lateinit var switchBtn : SwitchCompat
    lateinit var categoriesBtn: ImageView //the 2 imgView that manage fragments
    lateinit var removeFragBtn: ImageView
    lateinit var date: TextView

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        supportActionBar?.hide()

        switchBtn= findViewById(R.id.idSwitch)  //the 2 imgView that manage fragments
        categoriesBtn= findViewById(R.id.statistiks_iv)
        removeFragBtn= findViewById(R.id.remvFrag_iv)

        db= Firebase.firestore
        auth = Firebase.auth


        //reading from database with user identification
        /*
        var user= auth.currentUser
        if(user!=null){
            db.collection("users")
                .document(user.uid).collection("receipts")
                .addSnapshotListener{ snapshot, e->
                    if(snapshot!=null){
                        for(document in snapshot.documents){
                            val item= document.toObject<Receipt>()
                            Log.d("!!!","${item}")
                            receiptBills.add(item!!)
                        }
                        Log.d("!!!","${receiptBills.size}") //full
                    }
                    Log.d("!!!","${receiptBills.size}") //full
                }
            //empty
        }*/

        //links the RV-Layout with the View      // RV in Activity
        recyclerView=findViewById(R.id.categ_RV)
        recyclerView.layoutManager= LinearLayoutManager(this)
        setRecyclerDbLista()

                                                //Switch btn for Budget popup window
        switchBtn.setOnClickListener{
            if (switchBtn.isChecked){
                createBudgeDialog()
                switchBtn.isChecked=false

                Log.d("!!!", "${switchBtn.isChecked}")
            }else{
                Log.d("!!!", "${switchBtn.isChecked}")
            }
        }


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
        if(categoriesFragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(categoriesFragment)
            transaction.commit()
        }else{
            Toast.makeText(this,"Categories Fragment not found", Toast.LENGTH_SHORT).show()
        }

    }
    //creates pop up window for budget
    fun createBudgeDialog(){
        val budget= BudgetDialog()
        budget.show(supportFragmentManager,"!!!")
    }
    fun setRecyclerDbLista():List<Receipt>{
        val list= mutableListOf<Receipt>()
        val user= auth.currentUser
        if(user!=null){
            db.collection("users")
                .document(user.uid).collection("receipts")
                .addSnapshotListener{ snapshot, e->
                    if(snapshot!=null){
                        for(document in snapshot.documents){
                            val item= document.toObject<Receipt>()
                            Log.d("!!!","${item}")
                            list.add(item!!)
                        }
                        Log.d("!!!","${list.size}") //full
                    }
                    dbRecyclerView(list)
                    Log.d("!!!","${list.size}") //full
                }
            //empty
        }
        return list
    }
    fun dbRecyclerView(list: List<Receipt>){
        val adapter=ExpensesRecyclerAdapter(this,list)
        recyclerView.adapter= adapter

    }
    //just creates data i can use for testing
    fun addDataTillUser(){
        val check1 = Receipt(sum=500, company="IKEA", category = "Housing", paymentmethod = "card")
        val check2 = Receipt(sum=800, company="Nintendo", category = "Electronics", paymentmethod = "card")
        val check3 = Receipt(sum=450, company="Sats", category = "Sports", paymentmethod = "cash")
        val check4 = Receipt(sum=980, company="SL", category = "Transport", paymentmethod = "card")
        val check5 = Receipt(sum=5000, company="IKEA", category = "Housing", paymentmethod = "cash")
        val check6 = Receipt(sum=780, company="ICA", category = "Groceries", paymentmethod = "cash")
        val check7 = Receipt(sum=320, company="Coop", category = "Groceries", paymentmethod = "card")
        val check8 = Receipt(sum=560, company="Starbucks", category = "Fika", paymentmethod = "card")
        val check9 = Receipt(sum=7659, company="SAS", category = "Travel", paymentmethod = "card")
        val check10 = Receipt(sum=4900, company="Microsoft", category = "Electronics", paymentmethod = "card")
        val check11 = Receipt(sum=20000, company="Apple", category = "Electronics", paymentmethod = "card")
        val check12 = Receipt(sum=3645, company="Google", category = "Electronics", paymentmethod = "card")
        /*val user= auth.currentUser
        if (user==null){
            return
        }*/
        val user= auth.currentUser ?: return //it declares user and at the same time if it's null returns back, saving the steps before
        //add one by one the data above
        db.collection("users").document(user.uid)
            .collection("receipts")
            .add(check1).addOnCompleteListener {
                Log.d("!!!","Receipt Added")
            }


    }



}