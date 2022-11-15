package com.example.cashy

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class BudgetDialog() : DialogFragment() {
    lateinit var cancelPop: Button
    lateinit var savePop: Button
    lateinit var budgetEditTxt: EditText
    lateinit var rentView: TextView
    lateinit var rentEditTxt: EditText
    var popUpBudget=""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            //dismiss()
            if(rentEditTxt.text.toString()!=""){
                budgetEditTxt.setText(rentEditTxt.text)
                popUpBudget=budgetEditTxt.text.toString()+"kr"
                dismiss()

            }


        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}