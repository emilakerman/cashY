package com.example.cashy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FullScreenAdapter(var receipts : List<Receipt>) :
    RecyclerView.Adapter<FullScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_full_screen, parent, false)
        return ViewHolder(itemView)
    }
    fun filterList(filterlist : List<Receipt>) {
        receipts = filterlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receipt = receipts[position]
        holder.expense_txtView.text = receipt.sum.toString()
        holder.category_txtView.text = receipt.category
        holder.paymentMethod_txtView.text = receipt.paymentMethod
        holder.company_txtView.text = receipt.company
        holder.timestamp_txt.text = receipt.timestamp.toString()
        holder.comment_txt.text = receipt.notis
        holder.icon_img.setImageResource(receipt.img!!)
    }

    override fun getItemCount(): Int {
        return receipts.size
    }
    
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expense_txtView = itemView.findViewById<TextView>(R.id.expense_txt)
        val category_txtView = itemView.findViewById<TextView>(R.id.category_txt)
        val paymentMethod_txtView = itemView.findViewById<TextView>(R.id.paymentMethod_txt)
        val company_txtView = itemView.findViewById<TextView>(R.id.company_txt)
        val timestamp_txt = itemView.findViewById<TextView>(R.id.timestamp_txt)
        val comment_txt = itemView.findViewById<TextView>(R.id.comment_txt)
        val icon_img = itemView.findViewById<ImageView>(R.id.icon_img)
    }
}