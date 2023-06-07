package com.example.cashy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerAdapter(val context: Context, val receipt: List<Receipt>) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>()
{
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= layoutInflater.inflate(R.layout.card_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receipts= receipt[position]
        holder.categoryTextView.text=receipts.category
        holder.transactionTextView.text=receipts.fullDate //transaction deprecated LOL
        holder.imgImageView.setImageResource(receipts.img!!)
        holder.companytxtV.text=receipts.company.toString()
        holder.sumTxtV.text=receipts.sum.toString()+" kr"
        holder.payMethTxtV.text=receipts.paymentMethod.toString()
    }

    override fun getItemCount(): Int {
        return receipt.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var categoryTextView=itemView.findViewById<TextView>(R.id.item_category)
        var transactionTextView=itemView.findViewById<TextView>(R.id.item_transaction) //could be timestamp
        var imgImageView=itemView.findViewById<ImageView>(R.id.item_img)
        var companytxtV= itemView.findViewById<TextView>(R.id.companyTextView)
        var sumTxtV= itemView.findViewById<TextView>(R.id.sumCatTxView)
        var payMethTxtV= itemView.findViewById<TextView>(R.id.payMethodTextView)

        init {
            //here itemView.setOnClickListener{}
        }

    }

}