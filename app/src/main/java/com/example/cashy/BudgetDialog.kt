package com.example.cashy

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class BudgetDialog() : DialogFragment() {
    lateinit var cancelPop: Button
    lateinit var savePop: Button
    lateinit var testEtPop: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.budget_popup,container,false)
        cancelPop=view.findViewById(R.id.cancelBtn)
        savePop=view.findViewById(R.id.saveBtn)
        testEtPop=view.findViewById(R.id.popup_Et)

        cancelPop.setOnClickListener{
            dismiss()
        }
        savePop.setOnClickListener{
            //code the logic for the different options available
            dismiss()
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