package com.example.cashy

import android.content.Context
import android.os.Bundle
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


class CategoryFragment : Fragment() {

    lateinit var catRecyclerView: RecyclerView //catFragRecyclerView
    lateinit var catTitle: TextView
    lateinit var catSpiner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_category, container, false)
        catTitle=view.findViewById(R.id.catTxv)
        catSpiner=view.findViewById(R.id.catSpinner)
        catRecyclerView= view.findViewById(R.id.catFragRecyclerView)

        setUpSpinner()

        catRecyclerView.layoutManager = LinearLayoutManager(view.context) //dunno yet which
        val adapter=ExpensesRecyclerAdapter(view.context, DataManager.catBills) //getContext
        catRecyclerView.adapter= adapter
        //el rv del fragment=al recycler de la calse expenses

        return view
    }

    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    //    super.onViewCreated(view, savedInstanceState)
    //}

    fun setUpSpinner(){
        //adapter for array values [needs context(asosiated to fragment?), list(valuesarray), layout(predefined-LO)]
        val adapter = ArrayAdapter.createFromResource(catSpiner.context,
                R.array.Categories,
                android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinnerview adapter conects to values adapter
        catSpiner.adapter= adapter

        catSpiner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position)
                Toast.makeText(context,"Selected: $selectedItem ",Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

}