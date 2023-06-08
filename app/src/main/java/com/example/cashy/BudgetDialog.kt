package com.example.cashy

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment

class BudgetDialog : AppCompatDialogFragment() {
    private lateinit var cancelPop: Button
    private lateinit var savePop: Button
    private lateinit var budgetEditTxt: EditText
    private lateinit var rentView: TextView
    private lateinit var rentEditTxt: EditText
    var popUpBudget=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.budget_popup, container, false)
        cancelPop = view.findViewById(R.id.cancelBtn)
        savePop = view.findViewById(R.id.saveBtn)
        budgetEditTxt=view.findViewById(R.id.budgetET)

        rentView=view.findViewById(R.id.rentTxtView)
        rentEditTxt=view.findViewById(R.id.rentEtxt)


        cancelPop.setOnClickListener {
            dismiss()
        }
        
        savePop.setOnClickListener {
            //code the logic for the different options available
            if(rentEditTxt.text.isNotEmpty()){
                budgetEditTxt.text = rentEditTxt.text
                popUpBudget=budgetEditTxt.text.toString()+"Kr"
                Log.d("!!!","Budget is: $popUpBudget")
                dismiss()
            }
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        val view=inflater.inflate(R.layout.budget_popup, null)

        return super.onCreateDialog(savedInstanceState)
    }
}