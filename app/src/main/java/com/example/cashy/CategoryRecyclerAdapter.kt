package com.example.cashy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerAdapter(val context: Context,
                              val expenses: List<Expenses>):RecyclerView
                                .Adapter<CategoryRecyclerAdapter.ViewHolder>(){
    val layoutInflater=LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= layoutInflater.inflate(R.layout.card_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense= expenses[position]
        holder.categoryTextView.text=expense.category
        holder.transactionTextView.text=expense.transactions.toString()
        holder.imgImageView.setImageResource(expense.img!!) //setImageResource(images[position]) //setImageResource()
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var categoryTextView=itemView.findViewById<TextView>(R.id.item_category)
        var transactionTextView=itemView.findViewById<TextView>(R.id.item_transaction)
        var imgImageView=itemView.findViewById<ImageView>(R.id.item_img)

    }

}