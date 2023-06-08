package com.example.cashy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
// would like to move this file to the "fragments" folder, but it might break things
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"


class CalendarFragment : Fragment() {
    private var day: String? = null
    private var month: String? = null
    private var year: String? = null

    var db= Firebase.firestore
    var auth= Firebase.auth

    lateinit var dateRecyclerView: RecyclerView
    var byDateList= mutableListOf<Receipt>()
    lateinit var dateBox: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        dateBox=view.findViewById(R.id.dateCalEditTxt)
        dateRecyclerView= view.findViewById(R.id.calendarReView)
        dateRecyclerView.layoutManager= LinearLayoutManager(view.context)

        setRecyclerByDate()

        return view
    }
    private fun setAdapters(list: List<Receipt>) {
        val adapter=CategoryRecyclerAdapter(requireView().context, list) //getContext()
        dateRecyclerView.adapter= adapter
        //fragment RV adapter= categoryRV adapter
    }
    private fun setRecyclerByDate() {
        val user= auth.currentUser

        if (user!=null) {
            byDateList.clear()
            dateBox.setText("")
            Log.d("!!!","$user")
            db.collection("users").document(user.uid)
                .collection("receipts")
                .whereEqualTo("day", day?.padStart(2,'0'))
                .whereEqualTo("monthNo", month?.padStart(2,'0'))
                .whereEqualTo("year",year)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("!!!", "${document.id} => ${document.data}")
                        val item= document.toObject<Receipt>()
                        byDateList.add(item)
                    }
                    // this should done with resource strings with placeholders instead, like what the warning suggests /arvid
                    dateBox.setText("You selected day: $day.$month.$year")
                    setAdapters(byDateList)
                }
                .addOnFailureListener { exception ->
                    Log.w("!!!", "Error getting documents: ", exception)
                }
        }
    }
    //puts the constants in the variables i want
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getString(ARG_PARAM1)
            month = it.getString(ARG_PARAM2)
            year = it.getString(ARG_PARAM3)
        }
    }
    //gets the info and puts it in Constants
    companion object {
        @JvmStatic
        fun newInstance(day: String, month: String, year: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, day)
                    putString(ARG_PARAM2, month)
                    putString(ARG_PARAM3, year)
                }
            }
    }

}