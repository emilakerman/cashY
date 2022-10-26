package com.example.cashy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class FullScreenAdapter(val context : Context, var receipts : List<Receipt>) :
    RecyclerView.Adapter<FullScreenAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item_full_screen, parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: FullScreenAdapter.ViewHolder, position: Int) {
        val receipt = receipts[position]
        holder.expense_txtView.text = receipt.sum.toString()
        holder.category_txtView.text = receipt.category
        holder.paymentMethod_txtView.text = receipt.paymentmethod
        holder.company_txtView.text = receipt.company
        holder.timestamp_txt.text = receipt.timestamp.toString()
        holder.comment_txt.text = receipt.notis
    }
    override fun getItemCount(): Int {
        return receipts.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expense_txtView = itemView.findViewById<TextView>(R.id.expense_txt)
        var category_txtView = itemView.findViewById<TextView>(R.id.category_txt)
        var paymentMethod_txtView = itemView.findViewById<TextView>(R.id.paymentMethod_txt)
        var company_txtView = itemView.findViewById<TextView>(R.id.company_txt)
        var timestamp_txt = itemView.findViewById<TextView>(R.id.timestamp_txt)
        var comment_txt = itemView.findViewById<TextView>(R.id.comment_txt)
    }
}