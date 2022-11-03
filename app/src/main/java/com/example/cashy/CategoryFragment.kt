package com.example.cashy

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


class CategoryFragment : Fragment() {

    lateinit var catRecyclerView: RecyclerView
    lateinit var catTitle: TextView
    lateinit var catSpiner: Spinner

    var db= Firebase.firestore
    var auth= Firebase.auth

    var getBills= mutableListOf<Receipt>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_category, container, false)
        catTitle=view.findViewById(R.id.catTxv)
        catSpiner=view.findViewById(R.id.catSpinner)
        catRecyclerView= view.findViewById(R.id.catFragRecyclerView)

        setUpSpinner()

        catRecyclerView.layoutManager=LinearLayoutManager(view.context)

        return view
    }


    fun setUpSpinner(){
        //adapter for array values [needs context(associated to fragment?), list(values-Array), layout(predefined-LO)]
        val adapter = ArrayAdapter.createFromResource(catSpiner.context,
                R.array.Categories,
                android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinner-View adapter connects to values adapter
        catSpiner.adapter= adapter

        catSpiner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
                setRecyclerByCategory(selectedItem.toString())
                Toast.makeText(context,"Selected: $selectedItem ",Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    fun setRecyclerByCategory(category:String){
        val user= auth.currentUser
        if (user!=null) {
            getBills.clear()
            Log.d("!!!","$user")
            //receipts in cashy
            db.collection("users").document(user.uid)
                .collection("receipts")
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("!!!", "${document.id} => ${document.data}")
                        val item= document.toObject<Receipt>()
                        Log.d("!!!","${item}")
                        getBills.add(item)
                    }
                    Log.d("!!!","size: ${getBills.size}")
                    set_dbFragmentRv(getBills)
                }
                .addOnFailureListener { exception ->
                    Log.w("!!!", "Error getting documents: ", exception)
                }
        }
    }
    fun setRecyclerFragmentLista():List<Receipt> {
        val user=auth.currentUser
        if(user!=null){
            db.collection("users")
                .document(user.uid).collection("receipts")
                .addSnapshotListener{ snapshot, e->
                    if(snapshot!=null){
                        for(document in snapshot.documents){
                            val item= document.toObject<Receipt>()
                            Log.d("!!!","${item}")
                            getBills.add(item!!)
                        }
                        Log.d("!!!","${getBills.size}") //full
                    }
                    set_dbFragmentRv(getBills)
                    Log.d("!!!","${getBills.size}") //full
                }
            //empty
        }
        return getBills
    }
    fun set_dbFragmentRv(list: List<Receipt>){
        val adapter=ExpensesRecyclerAdapter(requireView().context, list) //getContext()
        catRecyclerView.adapter= adapter
        //fragment RV adapter= expensesRV adapter
    }

}