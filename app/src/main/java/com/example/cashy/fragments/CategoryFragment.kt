package com.example.cashy.fragments

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
import com.example.cashy.CategoryRecyclerAdapter
import com.example.cashy.R
import com.example.cashy.Receipt
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class CategoryFragment : Fragment() {

    private lateinit var catRecyclerView: RecyclerView
    private lateinit var catTitle: TextView
    private lateinit var catSpinner: Spinner

    private var db= Firebase.firestore
    var auth= Firebase.auth

    private var getBills= mutableListOf<Receipt>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_category, container, false)
        catTitle=view.findViewById(R.id.catTxv)
        catSpinner=view.findViewById(R.id.catSpinner)
        catRecyclerView= view.findViewById(R.id.catFragRecyclerView)

        setUpSpinner()

        catRecyclerView.layoutManager=LinearLayoutManager(view.context)

        return view
    }


    private fun setUpSpinner() {
        //adapter for array values [needs context(associated to fragment?), list(values-Array), layout(predefined-LO)]
        val adapter = ArrayAdapter.createFromResource(catSpinner.context,
            R.array.Categories,
                android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinner-View adapter connects to values adapter
        catSpinner.adapter= adapter

        catSpinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
                setRecyclerByCategory(selectedItem.toString())
                Toast.makeText(context,"Selected: $selectedItem ",Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun setRecyclerByCategory(category:String) {
        val user= auth.currentUser
        if (user!=null) {
            getBills.clear()
            Log.d("!!!","$user")
            db.collection("users").document(user.uid)
                .collection("receipts")
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("!!!", "${document.id} => ${document.data}")
                        val item= document.toObject<Receipt>()
                        getBills.add(item)
                    }
                    setAdapters(getBills)
                }
                .addOnFailureListener { exception ->
                    Log.w("!!!", "Error getting documents: ", exception)
                }
        }
    }

    private fun setAdapters(list: List<Receipt>) {
        val adapter= CategoryRecyclerAdapter(requireView().context, list) //getContext()
        catRecyclerView.adapter= adapter
        //fragment RV adapter= expensesRV adapter
    }

}