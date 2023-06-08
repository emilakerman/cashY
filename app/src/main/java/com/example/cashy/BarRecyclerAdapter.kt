package com.example.cashy

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarRecyclerAdapter (val context: Context,
                          val receipt: List<Receipt>): RecyclerView.Adapter<BarRecyclerAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= layoutInflater.inflate(R.layout.bar_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receipts= receipt[position]
        when(receipts.category) {
            // this should done with resource strings instead, like what the warning suggests /arvid
            "1"-> holder.box1Txt.text="Jan"
            "2"-> holder.box1Txt.text="Feb"
            "3"-> holder.box1Txt.text="Mar"
            "4"-> holder.box1Txt.text="Apr"
            "5"-> holder.box1Txt.text="May"
            "6"-> holder.box1Txt.text="Jun"
            "7"-> holder.box1Txt.text="Jul"
            "8"-> holder.box1Txt.text="Aug"
            "9"-> holder.box1Txt.text="Sep"
            "10"-> holder.box1Txt.text="Oct"
            "11"-> holder.box1Txt.text="Nov"
            "12"-> holder.box1Txt.text="Dec"
        }

        holder.bar.max=getMaxValue()
        barCurrentProgress(receipts.sum!!,holder.bar)
        //holder.box1Txt.text=receipts.monthNo
        holder.box2Txt.text=receipts.year
    }

    override fun getItemCount(): Int {
        return receipt.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var bar=itemView.findViewById<ProgressBar>(R.id.progressRvBar)
        var box1Txt=itemView.findViewById<TextView>(R.id.txtBar1)
        var box2Txt=itemView.findViewById<TextView>(R.id.txtBar2)
    }

    private fun getMaxValue(): Int {
        val listOfNumbers = mutableListOf<Int>()
        for (item in receipt) {
            listOfNumbers.add(item.sum!!)
        }
        return listOfNumbers.max()
    }

    private fun barCurrentProgress(progress: Int, view:ProgressBar) {
        ObjectAnimator
            .ofInt(view, "progress", progress)
            .setDuration(2000)
            .start()
    }

}